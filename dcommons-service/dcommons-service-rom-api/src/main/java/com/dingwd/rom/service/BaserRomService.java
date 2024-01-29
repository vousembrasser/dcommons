package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaserRomService<DO, ID> implements IRomService<DO, ID> {

    @Inject
    private IBaseRepository<DO, ID> dao;

    public <T extends IBaseRepository<DO, ID>> T getDao() {
        return (T) dao;
    }

    @Override
    public DO save(DO model) {
        return dao.saveAndFlush(model);
    }

    @Override
    public List<DO> saves(List<DO> models) {
        for (DO model : models) {
            Assert.notNull(model, "The given model must not be null!");
            dao.saveAndFlush(model);
        }
        return models;
    }

    @Override
    public DO update(DO model) {
        return dao.updateById(model);
    }

    @Override
    public boolean remove(ID id) {
        dao.removeById(id);
        return true;
    }

    @Override
    public boolean removes(List<ID> id) {
        dao.removeAllById(id);
        return true;
    }

    @Override
    public DO get(ID id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<DO> list(QueryBuild queryBuild) {
        return dao.findAll(queryBuild);
    }

    @Override
    public List<DO> list() {
        return dao.findAll();
    }

    @Override
    public Page<DO> page(QueryBuild queryBuild, Pageable pageable) {
        Page<DO> modelPage = dao.findAll(queryBuild, pageable);
        List<DO> list = modelPage.stream().toList();
        return new PageImpl<>(list, pageable, modelPage.getTotalElements());
    }
}
