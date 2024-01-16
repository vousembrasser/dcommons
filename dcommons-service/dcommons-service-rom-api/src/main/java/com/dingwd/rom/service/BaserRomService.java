package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaserRomService<BO, DO, ID> implements IRomService<BO, DO, ID> {

    @Inject
    private IBaseRepository<DO, ID> dao;

    public <T extends IBaseRepository<DO, ID>> T getDao() {
        return (T) dao;
    }

    public abstract DO vo2DO(BO vo, DO source);

    public abstract BO model2BO(DO model, BO source);

    public abstract List<BO> model2BO(List<DO> model);

    public abstract QueryBuild query(BO vo);

    @Override
    public BO insert(BO vo) {
        DO model = vo2DO(vo, null);
        model = dao.saveAndFlush(model);
        return model2BO(model, vo);
    }

    @Override
    public List<BO> inserts(List<BO> vos) {
        for (BO vo : vos) {
            Assert.notNull(vo, "The given model must not be null!");
            DO model = vo2DO(vo, null);
            model = dao.saveAndFlush(model);
            model2BO(model, vo);
        }
        return vos;
    }

    @Override
    public BO update(BO vo) {
        DO model = vo2DO(vo, null);
        model = dao.updateById(model);
        return model2BO(model, vo);
    }

    @Override
    public boolean delete(ID id) {
        dao.deleteById(id);
        return true;
    }

    @Override
    public boolean deletes(List<ID> id) {
        dao.deleteAllById(id);
        return true;
    }

    @Override
    public BO get(ID id) {
        DO model = dao.getReferenceById(id);
        return model2BO(model, null);
    }

    @Override
    public List<BO> list(BO vo) {
        List<DO> entities = dao.findAll(query(vo));
        return model2BO(entities);
    }

    @Override
    public List<BO> list() {
        List<DO> entities = dao.findAll();
        return model2BO(entities);
    }

    @Override
    public Page<BO> page(BO vo, Pageable pageable) {
        Page<DO> modelPage = dao.findAll(query(vo), pageable);
        List<DO> list = modelPage.stream().toList();
        return new PageImpl<>(model2BO(list), pageable, modelPage.getTotalElements());
    }
}
