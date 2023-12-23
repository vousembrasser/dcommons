package com.dingwd.dcommons.rom.config;


import com.dingwd.dcommons.rom.jpa.BaseRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.dingwd", repositoryBaseClass = BaseRepositoryImpl.class)
public class JpaConfig {

    @Bean
    public void Test() {
        System.out.println("JpaConfig=======================");
    }
}
