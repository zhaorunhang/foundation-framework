package com.zrh.framework.core.configurations.knife4j;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.Resource;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.configurations
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/3
 * @TIME: 0:58
 * @Description:
 */
@Configuration
@ConditionalOnProperty(prefix = "open-api", name = "enable", havingValue = "true")
public class OpenApiConfiguration {
    @Resource
    private OpenApiProperties openApiProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(openApiProperties.getTitle())
                        .description(openApiProperties.getDescription())
                        .version(openApiProperties.getVersion())
                        .contact(new Contact()
                                .name(openApiProperties.getName())
                                .email(openApiProperties.getEmail()))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    @Bean
    public GroupedOpenApi restControllerApi() {
        return GroupedOpenApi.builder()
                .group(openApiProperties.getGroup())
                .displayName(openApiProperties.getDisplayName())
                .packagesToScan(openApiProperties.getPackagesToScan())  // 基础包
                .addOperationCustomizer(restControllerFilter())
                .build();
    }

    private OperationCustomizer restControllerFilter() {
        return (operation, handlerMethod) -> {
            // 可以在这里添加额外的过滤逻辑
            // 默认已经只扫描 @RestController 和 @Controller
            // 获取处理方法所属的类
            Class<?> beanType = handlerMethod.getBeanType();
            // 检查类是否有 @RestController 注解
            if (beanType.isAnnotationPresent(RestController.class)) {
                return operation;  // 保留该接口
            }
            return null;
        };
    }
}
