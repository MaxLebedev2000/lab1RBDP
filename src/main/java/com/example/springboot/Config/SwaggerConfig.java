package com.example.springboot.Config;

import lombok.*;
import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import java.util.Collections;

@Configuration
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig {
    @Bean
    public Docket externalApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springboot.Controller"))
                .paths(PathSelectors.regex("/.+"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("default");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Студопедия", "Ваша Школопедия - https://studopedia.ru",
                "1.0", null, null, null, null, Collections.emptyList());
    }
}
