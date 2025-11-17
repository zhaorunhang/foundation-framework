package com.zrh.framework.core.advices;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.advices
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/13
 * @TIME: 16:33
 * @Description:
 */
@ControllerAdvice
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "response.advice", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DynamicResponseAdviceWrapper implements ResponseBodyAdvice<Object> {

    private final DynamicResponseAdvice delegate;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return delegate.supports(returnType, converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        return delegate.beforeBodyWrite(body, returnType, selectedContentType,
                selectedConverterType, request, response);
    }
}
