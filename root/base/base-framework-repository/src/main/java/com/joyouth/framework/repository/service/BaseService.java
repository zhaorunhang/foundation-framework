package com.joyouth.framework.repository.service;

import com.joyouth.framework.repository.entitys.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.service
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/26
 * @TIME: 13:33
 * @Description:
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {
    List<T> findAll();

    List<T> findAll(T t, Integer pageSize, Integer pageIndex, String sortName) throws Exception;

    List<T> findAll(T t, Integer pageSize, Integer pageIndex) throws Exception;

    List<T> findAll(Example<T> example);

    List<T> findAll(Example<T> example, Sort sort);

    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec, Sort sort);

    T save(T entity);

    List<T> saveAll(Iterable<T> entities);

    Optional<T> findById(ID id);

    List<T> findAllById(Iterable<ID> ids);

    Optional<T> findOne(Example<T> example);

    Long count(T t) throws Exception;

    Long count(Specification<T> spec);

    Integer deleteIds(List<ID> ids, String deleterId);

}
