package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface IBaseRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteById(ID var1);

    void deleteAllById(Iterable<? extends ID> ids);

    T getReferenceById(ID var1);

    List<T> findAll(QueryBuild predicates);

    Page<T> findAll(QueryBuild predicates, Pageable pageable);

}
