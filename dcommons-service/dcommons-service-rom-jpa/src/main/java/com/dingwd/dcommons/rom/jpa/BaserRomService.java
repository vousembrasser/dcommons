package com.dingwd.dcommons.rom.jpa;

import com.dingwd.rom.service.IRomService;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class BaserRomService<Model, Entity, ID> implements IRomService<Model, Entity, ID> {

    @Inject
    private BaseRepository<Entity,ID> dao;


    public abstract Entity model2Entity(Model model);

    public abstract List<Entity> model2Entity(List<Model> model);


    public abstract Model entity2Model(Entity entity);

    public abstract List<Model> entity2Model(List<Entity> entity);

    public abstract Specification<Entity> query(Model model);

    @Override
    public Model insert(Model model) {
        Entity entity = model2Entity(model);
        entity = dao.saveAndFlush(entity);
        return entity2Model(entity);
    }

    @Override
    public List<Model> inserts(List<Model> model) {
        List<Entity> entity = model2Entity(model);
        return entity2Model(dao.saveAllAndFlush(entity));
    }

    @Override
    public boolean update(Model model) {
        Entity entity = model2Entity(model);
        dao.saveAndFlush(entity);
        return true;
    }

    @Override
    public boolean delete(ID id) {
        dao.deleteById(id);
        return false;
    }

    @Override
    public boolean deletes(List<ID> id) {
        dao.deleteAllById(id);
        return true;
    }

    @Override
    public Model get(ID id) {
        Entity entity = dao.getReferenceById(id);
        return entity2Model(entity);
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
        Page<Model> modelPage = new PageImpl<>(entity2Model(list), pageable, entityPage.getTotalElements());
        return modelPage;
    }
}
