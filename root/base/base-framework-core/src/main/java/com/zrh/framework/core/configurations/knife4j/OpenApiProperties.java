package com.zrh.framework.core.configurations.knife4j;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.configurations.knife4j
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/3
 * @TIME: 1:04
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "open-api")
public class OpenApiProperties {
    private boolean enable = true;
    private String title = "接口文档";
    private String description = "API 接口文档";
    private String version = "1.0.0";
    private String name = "ZRH";
    private String email = "zhaorunhang123@aliyun.com";
    private String group = "base";
    private String displayName = "RestController接口";
    private String packagesToScan = "com.zrh";

}
