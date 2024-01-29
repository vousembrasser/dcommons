package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IBaseRepository<DO, ID> extends Repository<DO, ID> {
    <DOs extends DO> DOs saveAndFlush(DOs model);

    <DOs extends DO> List<DOs> saveAllAndFlush(Iterable<DOs> models);

    void removeById(ID var1);

    void removeAllById(Iterable<? extends ID> ids);

    Optional<DO> findById(ID id);

    List<DO> findAll(QueryBuild predicates);
    List<DO> findAll();

    Page<DO> findAll(QueryBuild predicates, Pageable pageable);

    <DOs extends DO> DOs updateById(DOs model);
}
