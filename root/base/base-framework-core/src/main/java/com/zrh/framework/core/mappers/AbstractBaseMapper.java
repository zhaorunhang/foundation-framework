package com.zrh.framework.core.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @param <D> DTO 类型
 * @param <E> Entity 类型
 * @PACKAGE_NAME: com.zrh.framework.core.mappers
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/1
 * @TIME: 23:28
 * @Description: 通用 Mapper 抽象类
 */
public abstract class AbstractBaseMapper<D, V, E> {
    /**
     * 单个对象转换：Entity -> DTO
     */
    public abstract D toDto(E entity);

    /**
     * 单个对象转换：DTO -> Entity
     */
    public abstract E toEntity(D dto);

    /**
     * 单个对象转换：entity -> V
     */
    public abstract V toVo(E entity);

    /**
     * 更新已有 Entity 对象（DTO -> Entity）
     */
    public abstract void updateEntityFromDto(D dto, @MappingTarget E entity);

    /**
     * 列表转换：Entity -> DTO
     */
    public List<D> toDtoList(List<E> entityList) {
        if (entityList == null) return null;
        return entityList.stream().map(this::toDto).toList();
    }

    /**
     * 列表转换：Entity -> V
     */
    public List<V> toVoList(List<E> entityList) {
        if (entityList == null) return null;
        return entityList.stream().map(this::toVo).toList();
    }

    /**
     * 列表转换：DTO -> Entity
     */
    public List<E> toEntityList(List<D> dtoList) {
        if (dtoList == null) return null;
        return dtoList.stream().map(this::toEntity).toList();
    }

}
