package com.zrh.framework.core.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.dtos
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/1
 * @TIME: 23:11
 * @Description:
 */
@Data
public class DefaultBaseDto<ID extends Serializable> implements BaseDto<ID>, Serializable {
    private ID id;
}
