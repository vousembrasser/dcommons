package com.dingwd.rom.service.curd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SelectService <T, ID> {

     /**
      * 根据主键查询
      * @param id 主键
      * @return 查询结果,无结果时返回{@code null}
      */
     T get(ID id);

     /**
      * 根据多个主键查询
      * @param ids 主键集合
      * @return 查询结果,如果无结果返回空集合
      */
     List<T> query(Iterable<ID> ids);

     /**
      * 查询所有结果
      * @return 所有结果,如果无结果则返回空集合
      */
     List<T> queryAll();

     /**
      * 查询所有结果
      * @return 获取分页结果
      */

//     Page<T> queryPage(List<SearchFilter> filters , PageRequest pageRequest);
//
//     /**
//      * 根据多个条件查询列表数据
//      * @param filters
//      * @return
//      */
//     List<T> queryAll(List<SearchFilter> filters);
//
//     /**
//      * 根据多个条件查询列表数据，并排序
//      * @param filters
//      * @param sort
//      * @return
//      */
//     List<T> queryAll(List<SearchFilter> filters, Sort sort);
//
//     /**
//      * 根据的单个条件查询列表数据
//      * @param filter
//      * @return
//      */
//     List<T> queryAll(SearchFilter filter);
//
//     /**
//      * 根据的单个条件查询列表数据
//      * @param filter
//      * @param sort
//      * @return
//      */
//     List<T> queryAll(SearchFilter filter,Sort sort);
 }
