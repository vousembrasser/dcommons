package com.dingwd.rom.service.curd;

import java.util.List;

public interface InsertService<T, ID> {

     /**
      * 添加一条数据
      *
      * @param record 要添加的数据
      * @return 添加后生成的主键
      */T save(T record);

    List<T> save(Iterable<T> records);
 }