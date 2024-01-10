package com.dingwd.rom.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IRomService<VO, Entity, ID> {

    Entity vo2Entity(VO vo, Entity source);

    VO entity2VO(Entity entity, VO source);

    List<VO> entity2VO(List<Entity> entity);


    VO insert(VO vo);

    /**
     * 批量插入 复用insert
     */
    List<VO> inserts(List<VO> entities);

    /**
     * 更新对象
     */
    VO update(VO vo);

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
    VO get(ID id);

    List<VO> list(VO vo);

    List<VO> list();
    /**
     * 查询分页方法
     */
    Page<VO> page(VO vo, Pageable pageable);
}
