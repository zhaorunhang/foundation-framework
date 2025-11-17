package com.zrh.framework.core.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Locale;

/**
 * @PACKAGE_NAME: frameworks.response
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/6
 * @TIME: 16:54
 * @Description:
 */
@Schema(description = "统一响应结果")
public record ResponseResult<T>(
        @Schema(description = "状态码", example = "200") Integer code,
        @Schema(description = "状态标识", example = "SUCCESS") String status,
        @Schema(description = "提示消息", example = "请求成功") String message,
        @Schema(description = "响应数据") T data) {
    // ----------------- 通用失败 -----------------
    public static <T> ResponseResult<T> failed() {
        return new ResponseResult<>(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getStatus(), ResultEnum.EXCEPTION.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> failed(String message) {
        return new ResponseResult<>(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getStatus(), message, null);
    }

    public static <T> ResponseResult<T> failed(ResultEnum resultEnum) {
        return new ResponseResult<>(resultEnum.getCode(), resultEnum.getStatus(), resultEnum.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> failed(ResultEnum resultEnum, String message) {
        return new ResponseResult<>(resultEnum.getCode(), resultEnum.getStatus(), message, null);
    }

    public static <T> ResponseResult<T> failed(ResultEnum resultEnum, T data) {
        return new ResponseResult<>(resultEnum.getCode(), resultEnum.getStatus(), resultEnum.getDefaultMessage(), data);
    }

    public static <T> ResponseResult<T> failed(ResultEnum resultEnum, String message, T data) {
        return new ResponseResult<>(resultEnum.getCode(), resultEnum.getStatus(), message, data);
    }

    // ----------------- 成功 -----------------
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), ResultEnum.SUCCESS.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), ResultEnum.SUCCESS.getDefaultMessage(), data);
    }

    public static <T> ResponseResult<T> success(T data, String message) {
        return new ResponseResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), message, data);
    }

    public static <T> ResponseResult<T> success(T data, Locale locale) {
        return new ResponseResult<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getStatus(), ResultEnum.SUCCESS.getMessage(locale), data);
    }

    // ----------------- 系统异常 -----------------
    public static <T> ResponseResult<T> exception() {
        return new ResponseResult<>(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getStatus(), ResultEnum.EXCEPTION.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> exception(String message) {
        return new ResponseResult<>(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getStatus(), message, null);
    }

    public static <T> ResponseResult<T> exception(Locale locale) {
        return new ResponseResult<>(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getStatus(), ResultEnum.EXCEPTION.getMessage(locale), null);
    }

    // ----------------- 参数校验失败 -----------------
    public static <T> ResponseResult<T> validateFailed() {
        return new ResponseResult<>(ResultEnum.VALIDATE_FAILED.getCode(), ResultEnum.VALIDATE_FAILED.getStatus(), ResultEnum.VALIDATE_FAILED.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> validateFailed(String message) {
        return new ResponseResult<>(ResultEnum.VALIDATE_FAILED.getCode(), ResultEnum.VALIDATE_FAILED.getStatus(), message, null);
    }

    public static <T> ResponseResult<T> validateFailed(Locale locale) {
        return new ResponseResult<>(ResultEnum.VALIDATE_FAILED.getCode(), ResultEnum.VALIDATE_FAILED.getStatus(), ResultEnum.VALIDATE_FAILED.getMessage(locale), null);
    }

    // ----------------- 无效参数 -----------------
    public static <T> ResponseResult<T> invalidArgs() {
        return new ResponseResult<>(ResultEnum.INVALID_ARGS.getCode(), ResultEnum.INVALID_ARGS.getStatus(), ResultEnum.INVALID_ARGS.getDefaultMessage(), null);
    }

    public static <T> ResponseResult<T> invalidArgs(String message) {
        return new ResponseResult<>(ResultEnum.INVALID_ARGS.getCode(), ResultEnum.INVALID_ARGS.getStatus(), message, null);
    }

    public static <T> ResponseResult<T> invalidArgs(Locale locale) {
        return new ResponseResult<>(ResultEnum.INVALID_ARGS.getCode(), ResultEnum.INVALID_ARGS.getStatus(), ResultEnum.INVALID_ARGS.getMessage(locale), null);
    }

    // ----------------- 权限相关 -----------------
    public static <T> ResponseResult<T> unauthorized(T data) {
        return new ResponseResult<>(ResultEnum.NOT_LOGIN.getCode(), ResultEnum.NOT_LOGIN.getStatus(), ResultEnum.NOT_LOGIN.getDefaultMessage(), data);
    }

    public static <T> ResponseResult<T> forbidden(T data) {
        return new ResponseResult<>(ResultEnum.NOT_POWER.getCode(), ResultEnum.NOT_POWER.getStatus(), ResultEnum.NOT_POWER.getDefaultMessage(), data);
    }

    // ----------------- 通用工厂 -----------------
    public static <T> ResponseResult<T> of(Integer code, String status, String message, T data) {
        return new ResponseResult<>(code, status, message, data);
    }

    public static <T> ResponseResult<T> of(Integer code, String status, String message) {
        return new ResponseResult<>(code, status, message, null);
    }
}
