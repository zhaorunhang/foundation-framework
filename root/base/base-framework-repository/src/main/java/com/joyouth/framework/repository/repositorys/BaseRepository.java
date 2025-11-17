package com.joyouth.framework.repository.repositorys;

import com.joyouth.framework.repository.entitys.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    List<T> findAll(T t, Integer pageSize, Integer pageIndex, String sortName) throws Exception;

    List<T> findAll(T t, Integer pageSize, Integer pageIndex) throws Exception;

    Long count(T t) throws Exception;

    Integer softDeleteAllById(List<ID> ids, String deleter);
}
