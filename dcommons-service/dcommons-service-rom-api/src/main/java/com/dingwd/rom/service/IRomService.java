package com.dingwd.rom.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRomService<Model,Entity,ID> {

    Entity model2Entity(Model model);

    List<Entity> model2Entity(List<Model> model);


    Model entity2Model(Entity entity);

    List<Model> entity2Model(List<Entity> entity);


    Model insert(Model model);

    /**
     * 批量插入 复用insert
     */
    List<Model> inserts(List<Model> entities);

    /**
     * 更新对象
     */
    boolean update(Model model);

    /**
     * 删除对象
     * 主键使用identifiedArray会按,逗号分割主键以批量更新
     */
    boolean delete(ID id);

    /**
     * 批量删除
     * 循环复用delete方法
     * 若仅根据主键按逗号分割 更新数据相同delete方法即可支持
     * 用次方法目的仅在于复用delete方法更新数据体不同时
     */
    boolean deletes(List<ID> id);

    /**
     * 查询单个对象
     */
    Model get(ID id);

    List<Model> list(Model model);

    /**
     * 查询分页方法
     */
    Page<Model> page(Model model, Pageable pageable);
}
