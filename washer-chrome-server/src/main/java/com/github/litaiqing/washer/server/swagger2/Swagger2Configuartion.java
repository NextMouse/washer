package com.github.litaiqing.washer.server.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/15 10:46
 * @since JDK 1.8
 */
@EnableSwagger2
@Configuration
public class Swagger2Configuartion {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.litaiqing.washer.server"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Washer Server REST API")
                .description("容器HeadlessBrowser多任务服务")
                .termsOfServiceUrl("https://github.com/litaiqing/washer")
                .contact(new Contact("litaiqing", "https://github.com/litaiqing/washer", "ohbugs@foxmail.com"))
                .version("1.0.0")
                .build();
    }

}
