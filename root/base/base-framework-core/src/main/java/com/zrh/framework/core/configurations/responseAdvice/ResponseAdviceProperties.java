package com.zrh.framework.core.configurations.responseAdvice;

import lombok.Data;

import java.util.Set;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.configurations.responseAdvice
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/13
 * @TIME: 15:16
 * @Description:
 */
@Data
public class ResponseAdviceProperties {
    private boolean enabled = false;
    private Set<String> basePackages = Set.of("com.zrh");
    private String beanName = "default";
}
