package com.zrh.framework.core.dtos;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.dtos
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/1
 * @TIME: 23:00
 * @Description:
 */
public interface BaseDto<ID extends Serializable> {
    ID getId();
}
