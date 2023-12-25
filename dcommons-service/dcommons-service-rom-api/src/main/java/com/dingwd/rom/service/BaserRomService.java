package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaserRomService<Model, Entity, ID> implements IRomService<Model, Entity, ID> {

    @Inject
    private IBaseRepository<Entity, ID> dao;

    public <T extends IBaseRepository<Entity, ID>> T getDao() {
        return (T) dao;
    }

    public abstract Entity model2Entity(Model model, Entity source);

    public abstract Model entity2Model(Entity entity, Model source);

    public abstract List<Model> entity2Model(List<Entity> entity);

    public abstract QueryBuild query(Model model);

    @Override
    public Model insert(Model model) {
        Entity entity = model2Entity(model, null);
        entity = dao.saveAndFlush(entity);
        return entity2Model(entity, model);
    }

    @Override
    public List<Model> inserts(List<Model> models) {
        for (Model model : models) {
            Assert.notNull(model, "The given entity must not be null!");
            Entity entity = model2Entity(model, null);
            entity = dao.saveAndFlush(entity);
            entity2Model(entity, model);
        }
        return models;
    }

    @Override
    public Model update(Model model) {
        Entity entity = model2Entity(model, null);
        entity = dao.updateById(entity);
        return entity2Model(entity, model);
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
    public Model get(ID id) {
        Entity entity = dao.getReferenceById(id);
        return entity2Model(entity, null);
    }

    @Override
    public List<Model> list(Model model) {
        List<Entity> entities = dao.findAll(query(model));
        return entity2Model(entities);
    }

    @Override
    public Page<Model> page(Model model, Pageable pageable) {
        Page<Entity> entityPage = dao.findAll(query(model), pageable);
        List<Entity> list = entityPage.stream().toList();
        return new PageImpl<>(entity2Model(list), pageable, entityPage.getTotalElements());
    }
}
