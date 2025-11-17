package com.zrh.framework.core.exception;

import lombok.Getter;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.exception
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/3
 * @TIME: 17:45
 * @Description:
 */
@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
