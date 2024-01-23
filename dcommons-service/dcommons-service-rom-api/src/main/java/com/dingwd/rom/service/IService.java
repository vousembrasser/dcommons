package com.dingwd.rom.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IService<BO, ID> {


    BO save(BO model);

    /**
     * 批量插入 复用save
     */
    List<BO> saves(List<BO> models);

    /**
     * 更新对象
     */
    boolean update(BO model);

    /**
     * 删除对象
     * 主键使用identifiedArray会按,逗号分割主键以批量更新
     */
    boolean remove(ID id);

    /**
     * 批量删除
     * 循环复用remove方法
     * 若仅根据主键按逗号分割 更新数据相同remove方法即可支持
     * 用次方法目的仅在于复用remove方法更新数据体不同时
     */
    boolean removes(List<ID> id);

    /**
     * 查询单个对象
     */
    BO get(ID id);

    List<BO> list(BO bo);

    List<BO> list();

    /**
     * 查询分页方法
     */
    Page<BO> page(BO bo, Pageable pageable);
}
