package com.zrh.framework.core.advices;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.zrh.framework.core.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Set;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.configurations.responseAdvice
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/13
 * @TIME: 15:17
 * @Description: 动态 Advice
 */
@Slf4j
@ConditionalOnProperty(prefix = "response.advice", name = "enabled", havingValue = "true", matchIfMissing = true)
public record DynamicResponseAdvice(Set<String> basePackages) implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 包扫描控制
        String packageName = returnType.getContainingClass().getPackageName();
        if (basePackages.stream().noneMatch(packageName::startsWith)) {
            return false;
        }
        Class<?> parameterType = returnType.getParameterType();
        // 排除已经包装过的 ResponseResult
        if (ResponseResult.class.isAssignableFrom(parameterType)) return false;
        // 排除 JSONObject
        if ("net.minidev.json.JSONObject".equals(parameterType.getName())) return false;
        // 排除 OAuth2Exception 等特定类型
        return !returnType.getGenericParameterType().getTypeName().contains("OAuth2Exception");
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        // 处理逻辑
        // String 类型特殊处理，保证前端接收 JSON
        if (body instanceof String strBody) {
            try {
                return JSON.toJSONString(wrapBody(strBody));
            } catch (JSONException e) {
                log.error("String 类型统一返回序列化失败", e);
                throw new IllegalStateException(e);
            }
        }
        return wrapBody(body);
    }

    private Object wrapBody(Object body) {
        if (body == null) return ResponseResult.success();
        if (body instanceof ResponseResult) return body;
        return ResponseResult.success(body);
    }
}
