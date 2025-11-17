package com.zrh.framework.core.functionals;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

@FunctionalInterface
public interface ResponseWrapperCallback {
    Object wrap(Object body, MethodParameter returnType, MediaType mediaType,
                ServerHttpRequest request, ServerHttpResponse response);
}
