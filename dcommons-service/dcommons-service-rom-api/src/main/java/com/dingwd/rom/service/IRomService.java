package com.dingwd.rom.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRomService<BO, DO, ID> {

    DO bo2DO(BO bo, DO source);

    BO model2BO(DO model, BO source);

    List<BO> model2BO(List<DO> model);


    BO insert(BO bo);

    /**
     * 批量插入 复用insert
     */
    List<BO> inserts(List<BO> entities);

    /**
     * 更新对象
     */
    BO update(BO bo);

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
    BO get(ID id);

    List<BO> list(BO bo);

    List<BO> list();
    /**
     * 查询分页方法
     */
    Page<BO> page(BO bo, Pageable pageable);
}
