package com.ai.blog.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@Configuration
public class Knife4jConfig {

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
                                .url("https://www.talkxj.com/api")
                                .description("生产环境服务地址")));
    }
}
