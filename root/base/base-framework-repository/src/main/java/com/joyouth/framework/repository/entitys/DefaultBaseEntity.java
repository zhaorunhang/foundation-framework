package com.joyouth.framework.repository.entitys;

import com.joyouth.framework.repository.entitys.annotations.UUIDGeneration;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.entitys
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/6
 * @TIME: 18:48
 * @Description:
 */
@Data
@MappedSuperclass
public abstract class DefaultBaseEntity<ID extends Serializable> implements BaseEntity<ID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuidStrategy")
    @UUIDGeneration
    private ID id;

}
