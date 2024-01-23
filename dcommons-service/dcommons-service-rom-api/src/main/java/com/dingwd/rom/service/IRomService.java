package com.dingwd.rom.service;


import com.dingwd.rom.service.query.QueryBuild;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRomService<DO, ID> {

    DO save(DO model);

    /**
     * 批量插入 复用save
     */
    List<DO> saves(List<DO> bos);

    /**
     * 更新对象
     */
    DO update(DO model);

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
    DO get(ID id);

    List<DO> list(QueryBuild queryBuild);

    List<DO> list();
    /**
     * 查询分页方法
     */
    Page<DO> page(QueryBuild queryBuild, Pageable pageable);
}
