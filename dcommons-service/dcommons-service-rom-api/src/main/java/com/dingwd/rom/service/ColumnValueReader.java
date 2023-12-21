package com.dingwd.rom.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class ColumnValueReader {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, String> columnValues = new HashMap<>();

        // 获取所有标记为@Entity的bean
        Map<String, Object> entityBeans = context.getBeansWithAnnotation(Entity.class);

        for (Object bean : entityBeans.values()) {
            Class<?> beanType = bean.getClass();
            // 遍历所有字段，查找@Column注解
            for (Field field : beanType.getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    // 只处理有具体列名的@Column注解
                    if (!column.name().isEmpty()) {
                        columnValues.put(field.getName(), column.name());
                    }
                }
            }
        }
    }
}