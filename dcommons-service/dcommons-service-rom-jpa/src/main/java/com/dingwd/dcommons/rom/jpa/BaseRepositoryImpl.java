package com.dingwd.dcommons.rom.jpa;

import com.dingwd.rom.service.IBaseRepository;
import com.dingwd.rom.service.query.QueryBuild;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

@NoRepositoryBean
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements IBaseRepository<T, ID> {

    private final EntityManager entityManager;
    protected static EntityManager ENTITY_MANAGER;
    private @Nullable CrudMethodMetadata metadata;
    private final JpaEntityInformation<T, ?> entityInformation;
    private final PersistenceProvider provider;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
        ENTITY_MANAGER = entityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager, JpaEntityInformation<T, ?> entityInformation) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
        ENTITY_MANAGER = entityManager;
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return super.saveAndFlush(entity);
    }

    @Override
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return super.saveAllAndFlush(entities);
    }

    @Override
    public void deleteById(ID id) {
        super.deleteById(id);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        super.deleteAllById(ids);
    }

    @Override
    public T getReferenceById(ID id) {
        return super.getReferenceById(id);
    }

    //    @Override
    public List<T> findAll(QueryBuild predicates) {
        return getQueryAndSort(predicates, getDomainClass(), Sort.unsorted()).getResultList();
    }

    public List<T> findAll() {
        return getQuery(null, getDomainClass(), Sort.unsorted()).getResultList();
    }

    //    @Override
    public Page<T> findAll(QueryBuild predicates, Pageable pageable) {

        Class<T> domainClass = getDomainClass();

        TypedQuery<T> query = getQuery(predicates, domainClass, pageable);

        return pageable.isUnpaged() ? new PageImpl<>(query.getResultList()) : readPage(query, domainClass, pageable, predicates);
    }

    @Transactional
    @Override
    public <S extends T> S updateById(S entity) {

        Assert.notNull(entity, "Entity must not be null");
        Assert.notNull(entityInformation.getId(entity), "Entity id must not be null");

        S result = entityManager.merge(entity);
        flush();
        return result;
    }

    protected Page<T> readPage(TypedQuery<T> query, final Class<T> domainClass, Pageable pageable,
                               QueryBuild predicates) {

        if (pageable.isPaged()) {
            query.setFirstResult(PageableUtils.getOffsetAsInteger(pageable));
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.getResultList(), pageable,
                () -> executeCountQuery(getCountQuery(predicates, domainClass)));
    }


    protected TypedQuery<T> getQuery(QueryBuild predicates, Class<T> domainClass,
                                     Pageable pageable) {

        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQueryAndSort(predicates, domainClass, sort);
    }


    protected TypedQuery<T> getQueryAndSort(QueryBuild predicates, Class<T> domainClass, Sort sort) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);

        Assert.notNull(domainClass, "Domain class must not be null");
        Assert.notNull(query, "CriteriaQuery must not be null");

        Root<T> root = query.from(domainClass);
        query.select(root);

        if (predicates != null) {
            query.where(predicates.done(builder, root));
        }

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return applyRepositoryMethodMetadata(entityManager.createQuery(query));
    }

    protected TypedQuery<Long> getCountQuery(QueryBuild predicates, Class<T> domainClass) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Assert.notNull(domainClass, "Domain class must not be null");
        Assert.notNull(query, "CriteriaQuery must not be null");

        Root<T> root = query.from(domainClass);

        if (predicates != null) {
            query.where(predicates.done(builder, root));
        }

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.emptyList());

        return applyRepositoryMethodMetadataForCount(entityManager.createQuery(query));
    }

    private <S> TypedQuery<S> applyRepositoryMethodMetadataForCount(TypedQuery<S> query) {

        if (metadata == null) {
            return query;
        }

        applyQueryHintsForCount(query);

        return query;
    }

    private void applyQueryHintsForCount(Query query) {

        if (metadata == null) {
            return;
        }

        getQueryHintsForCount().forEach(query::setHint);
        applyComment(metadata, query::setHint);
    }

    private void applyComment(CrudMethodMetadata metadata, BiConsumer<String, Object> consumer) {

        if (metadata.getComment() != null && provider.getCommentHintKey() != null) {
            consumer.accept(provider.getCommentHintKey(), provider.getCommentHintValue(this.metadata.getComment()));
        }
    }


    private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {

        if (metadata == null) {
            return query;
        }

        LockModeType type = metadata.getLockModeType();
        TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

        applyQueryHints(toReturn);

        return toReturn;
    }

    private void applyQueryHints(Query query) {

        if (metadata == null) {
            return;
        }

        getQueryHints().withFetchGraphs(entityManager).forEach(query::setHint);
        applyComment(metadata, query::setHint);
    }


    private static long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    protected Class<T> getDomainClass() {
        return entityInformation.getJavaType();
    }

    public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
        this.metadata = crudMethodMetadata;
    }

}
