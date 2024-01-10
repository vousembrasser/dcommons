package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import jakarta.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaserRomService<VO, Entity, ID> implements IRomService<VO, Entity, ID> {

    @Inject
    private IBaseRepository<Entity, ID> dao;

    public <T extends IBaseRepository<Entity, ID>> T getDao() {
        return (T) dao;
    }

    public abstract Entity vo2Entity(VO vo, Entity source);

    public abstract VO entity2VO(Entity entity, VO source);

    public abstract List<VO> entity2VO(List<Entity> entity);

    public abstract QueryBuild query(VO vo);

    @Override
    public VO insert(VO vo) {
        Entity entity = vo2Entity(vo, null);
        entity = dao.saveAndFlush(entity);
        return entity2VO(entity, vo);
    }

    @Override
    public List<VO> inserts(List<VO> vos) {
        for (VO vo : vos) {
            Assert.notNull(vo, "The given entity must not be null!");
            Entity entity = vo2Entity(vo, null);
            entity = dao.saveAndFlush(entity);
            entity2VO(entity, vo);
        }
        return vos;
    }

    @Override
    public VO update(VO vo) {
        Entity entity = vo2Entity(vo, null);
        entity = dao.updateById(entity);
        return entity2VO(entity, vo);
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
    public VO get(ID id) {
        Entity entity = dao.getReferenceById(id);
        return entity2VO(entity, null);
    }

    @Override
    public List<VO> list(VO vo) {
        List<Entity> entities = dao.findAll(query(vo));
        return entity2VO(entities);
    }

    @Override
    public List<VO> list() {
        List<Entity> entities = dao.findAll();
        return entity2VO(entities);
    }

    @Override
    public Page<VO> page(VO vo, Pageable pageable) {
        Page<Entity> entityPage = dao.findAll(query(vo), pageable);
        List<Entity> list = entityPage.stream().toList();
        return new PageImpl<>(entity2VO(list), pageable, entityPage.getTotalElements());
    }
}
