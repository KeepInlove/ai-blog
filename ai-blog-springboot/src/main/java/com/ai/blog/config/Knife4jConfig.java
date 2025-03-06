package com.ai.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class Knife4jConfig {

    // 公共参数自定义器
    @Bean
    public OperationCustomizer globalHeaderParameter() {
        return (operation, handlerMethod) -> {
            Parameter parameter = new Parameter()
                    .in("header")
                    .name("Authorization")
                    .description("Bearer Token")
                    .required(true)
                    .schema(new StringSchema());
            operation.addParametersItem(parameter);
            return operation;
        };
    }

    // 公共OpenAPI配置
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ai博客API文档")
                        .version("1.0")
                        .description("基于SpringBoot3+Vue开发的博客系统")
                        .contact(new Contact()
                                .name("菜丶队")
                                .url("https://github.com/KeepInlove")
                                .email("904004593@qq.com")))
                .servers(Collections.singletonList(
                        new Server()
                                .url("https://127.0.0.1:8080/api")
                                .description("生产环境服务地址")));
    }

    // 分组配置（新增部分）
    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("common")
                .displayName("通用接口")
                .addOperationCustomizer(globalHeaderParameter())
                .packagesToScan("com.ai.blog.controller.common")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .displayName("用户端接口")
                .addOperationCustomizer(globalHeaderParameter())
                .packagesToScan("com.ai.blog.controller.web")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .displayName("管理端接口")
                .addOperationCustomizer(globalHeaderParameter())
                .packagesToScan("com.ai.blog.controller.admin")
                .build();
    }
}
