package com.joyouth.framework.repository.entitys;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: frameworks.entitys
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/6
 * @TIME: 18:33
 * @Description:
 */
public interface BaseEntity<ID extends Serializable> {
    ID getId();
}
