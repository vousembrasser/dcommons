package com.dingwd.rom.service;

import com.dingwd.rom.service.query.QueryBuild;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BOService<DOService extends IRomService<DO, ID>, BO, DO, ID> {

    private final DOService doService;

    public BOService(DOService doService) {
        this.doService = doService;
    }

    public abstract DO bo2DO(BO bo, DO model);

    public abstract BO do2BO(DO model, BO bo);

    public abstract List<BO> do2BO(List<DO> list);

    public abstract QueryBuild query(BO bo);

    /**
     * 新增数据
     *
     * @param bo 实例对象
     * @return 实例对象
     */
    public BO save(BO bo) {
        DO model = bo2DO(bo, null);
        model = doService.save(model);
        return do2BO(model, bo);
    }

    public List<BO> saves(List<BO> bos) {
        Assert.notNull(bos, "The given model must not be null!");
        for (BO bo : bos) {
            this.save(bo);
        }
        return bos;
    }

    /**
     * 修改数据
     *
     * @param bo 实例对象
     * @return 实例对象
     */
    public boolean update(BO bo) {
        DO model = bo2DO(bo, null);
        doService.update(model);
        return true;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean remove(ID id) {
        return doService.remove(id);
    }

    public boolean removes(List<ID> list) {
        return doService.removes(list);
    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public BO get(ID id) {
        DO model = doService.get(id);
        return do2BO(model, null);
    }

    public List<BO> list(BO bo) {
        List<DO> models = doService.list(query(bo));
        return do2BO(models);
    }

    public List<BO> list() {
        List<DO> models = doService.list();
        return do2BO(models);
    }

    /**
     * 分页查询
     *
     * @param bo 筛选条件
     * @param pageable      分页对象
     * @return 查询结果
     */
    public Page<BO> page(BO bo, Pageable pageable) {
        Page<DO> modelPage = doService.page(query(bo), pageable);
        List<DO> list = modelPage.stream().toList();
        return new PageImpl<>(do2BO(list), pageable, modelPage.getTotalElements());
    }
}
