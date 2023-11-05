package com.dingwd.rom.service.curd;

public interface DeleteService<ID> {

  /**
   * 根据主键删除记录
   *
   * @param id 主键
   */
  void delete(ID id);

  /**
   * 根据主键删除记录
   *
   * @param ids 主键集合
   */
  void delete(Iterable<ID> ids);
 }