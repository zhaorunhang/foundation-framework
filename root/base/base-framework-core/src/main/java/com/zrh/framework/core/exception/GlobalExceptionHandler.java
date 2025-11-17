package com.zrh.framework.core.exception;

import cn.hutool.core.util.StrUtil;
import com.zrh.framework.core.response.ResponseResult;
import com.zrh.framework.core.response.ResultEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * @PACKAGE_NAME: frameworks.exception
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/6
 * @TIME: 16:50
 * @Description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // ----------------- 默认异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleException(Exception ex, HttpServletRequest request) {
        log.error("【业务】接口异常 接口接口地址：{} 错误信息：{}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseResult.exception();
    }

    // ----------------- 缺少请求参数 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String format = String.format("缺少参数：%s，类型：%s", ex.getParameterName(), ex.getParameterType());
        log.error("【参数缺失】接口地址：{} 错误信息：{}", request.getRequestURI(), format);
        return ResponseResult.validateFailed(format);
    }

    // ----------------- 参数类型不匹配 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseResult<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String format = String.format("参数类型不匹配，参数名：%s，期望类型：%s，实际值：%s",
                ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue());
        log.error("【参数类型错误】接口地址：{} 错误信息：{}", request.getRequestURI(), format);
        return ResponseResult.validateFailed(format);
    }

    // ----------------- JSON 解析异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("【JSON解析异常】接口地址：{} 错误信息：{}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseResult.validateFailed("JSON对象参数类型不正确或缺失必填字段");
    }

    // ----------------- 参数绑定异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public ResponseResult<String> handleBindException(BindException ex, HttpServletRequest request) {
        log.error("【参数绑定异常】接口地址：{} 错误信息：{}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseResult.validateFailed("请求参数数据不符合规范或绑定异常");
    }

    // ----------------- @Valid 参数校验异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        String msg = errors.isEmpty() ? "参数校验失败" : errors.get(0).getDefaultMessage();
        log.error("【参数校验异常】接口地址：{} 错误信息：{}", request.getRequestURI(), msg, ex);
        return ResponseResult.validateFailed(msg);
    }

    // ----------------- 单参数校验异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<String> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            log.error("【参数校验异常】接口地址：{} 错误信息：{}", request.getRequestURI(), violation.getMessage(), ex);
            return ResponseResult.validateFailed(violation.getMessage());
        }
        return ResponseResult.validateFailed("参数为空或错误");
    }


    // ----------------- 请求方法不支持 -----------------
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> handleMethodNotSupported(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        String format = String.format("请求方法不支持! 当前方法：%s，支持方法：%s",
                ex.getMethod(), ex.getSupportedMethods() != null ? ex.getSupportedMethods()[0] : "");
        log.error("【方法不支持】接口地址：{} 错误信息：{}", request.getRequestURI(), format, ex);
        return ResponseResult.failed(ResultEnum.ERROR, format);
    }

    // ----------------- 业务断言异常 -----------------
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult<String> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("【业务断言异常】接口地址：{} 错误信息：{}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseResult.failed(ResultEnum.ERROR, ex.getMessage());
    }

    // ----------------- 自定义业务异常 -----------------
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<String> handleBusinessException(BusinessException ex) {
        log.error("【业务异常】{}", ex.getMessage(), ex);
        return ResponseResult.of(ex.getCode(), ResultEnum.ERROR.getStatus(), ex.getMessage());
    }

}
