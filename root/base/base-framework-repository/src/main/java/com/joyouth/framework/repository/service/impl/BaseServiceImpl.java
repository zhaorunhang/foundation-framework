package com.joyouth.framework.repository.service.impl;

import com.joyouth.framework.repository.entitys.BaseEntity;
import com.joyouth.framework.repository.repositorys.BaseRepository;
import com.joyouth.framework.repository.service.BaseService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.service.impl
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/26
 * @TIME: 13:33
 * @Description:
 */

public class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

    @Setter
    @Getter
    private BaseRepository<T, ID> baseRepository;

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(T t, Integer pageSize, Integer pageIndex, String sortName) throws Exception {
        return baseRepository.findAll(t, pageSize, pageIndex, sortName);
    }

    @Override
    public List<T> findAll(T t, Integer pageSize, Integer pageIndex) throws Exception {
        return baseRepository.findAll(t, pageSize, pageIndex);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return baseRepository.findAll(example);
    }

    @Override
    public List<T> findAll(Example<T> example, Sort sort) {
        return baseRepository.findAll(example, sort);
    }

    @Override
    public List<T> findAll(Specification<T> specification){
        return baseRepository.findAll(specification);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return baseRepository.findAll(spec, sort);
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public List<T> saveAll(Iterable<T> entities) {
        return baseRepository.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    @Override
    public Optional<T> findOne(Example<T> example) {
        return baseRepository.findOne(example);
    }

    @Override
    public Long count(T t) throws Exception {
        return baseRepository.count(t);
    }

    @Override
    public Long count(Specification<T> spec) {
        return baseRepository.count(spec);
    }

    @Override
    public Integer deleteIds(List<ID> ids, String deleterId) {
        return baseRepository.softDeleteAllById(ids, deleterId);
    }
}
