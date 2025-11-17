package com.joyouth.framework.repository.repositorys.impl;

import ch.qos.logback.core.util.StringUtil;
import com.joyouth.framework.repository.entitys.BaseEntity;
import com.joyouth.framework.repository.repositorys.BaseRepository;
import com.joyouth.framework.repository.utils.ReflectiveFieldHandlerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.repositorys.impl
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/23
 * @TIME: 17:06
 * @Description:
 */
public class BaseRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll(T t, Integer pageSize, Integer pageIndex, String sortName) throws Exception {
        Class<T> tClass = this.getDomainClass();
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        Predicate[] restrictions = this.buildPredicates(builder, root, t);
        if (restrictions != null) {
            query.where(restrictions);
        }

        if (!StringUtil.isNullOrEmpty(sortName)) {
            Order order = builder.desc(root.get(sortName));
            query.orderBy(order);
        }

        return this.entityManager.createQuery(query).setMaxResults(pageSize).setFirstResult(pageIndex).getResultList();
    }

    public List<T> findAll(T t, Integer pageSize, Integer pageIndex) throws Exception {
        return this.findAll(t, pageSize, pageIndex, null);
    }

    @Override
    public Long count(T t) throws Exception {
        Class<T> tClass = this.getDomainClass();
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(tClass);
        query.select(builder.count(root));
        Predicate[] restrictions = this.buildPredicates(builder, root, t);
        if (restrictions != null) {
            query.where(restrictions);
        }

        return this.entityManager.createQuery(query).getSingleResult();
    }

    @Override
    @Transactional
    public Integer softDeleteAllById(List<ID> ids, String deleter) {
        List<T> entities = findAllById(ids);
        entities.forEach(entity -> {
            try {
                ReflectiveFieldHandlerUtil.getValueByPropName(entity, "deleted", true, true);
                ReflectiveFieldHandlerUtil.getValueByPropName(entity, "deleter", true, deleter);
                LocalDateTime deleteTime = LocalDateTime.now();
                ReflectiveFieldHandlerUtil.getValueByPropName(entity, "deleteTime", true, deleteTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        List<T> ts = saveAll(entities);
        return ts.size();
    }

    protected Predicate[] buildPredicates(CriteriaBuilder builder, Root<T> root, T t) throws IllegalAccessException {
        Class<T> tClass = this.getDomainClass();
        if (t == null) {
            return null;
        } else {
            Field[] fields = tClass.getDeclaredFields();
            List<Predicate> predicates = new ArrayList<>();

            for (Field field : fields) {
                field.setAccessible(true);
                String fName = field.getName();
                Predicate predicate = builder.equal(root.get(fName), field.get(t));
                predicates.add(predicate);
            }

            Predicate[] restrictions = new Predicate[predicates.size()];
            predicates.toArray(restrictions);
            return restrictions;
        }
    }
}
