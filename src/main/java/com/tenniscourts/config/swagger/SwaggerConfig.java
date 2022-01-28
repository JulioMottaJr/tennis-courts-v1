package com.tenniscourts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("Tennis Court Api")
                .description("Here you can create an admin, a guest, a tennis court, create a schedule to play on a court.\n" +
                        "\n" +
                        "Please make sure you've created 1 admin to use the api, some requests will ask for either a User ID or an Admin ID.\n" +
                        "\n" +
                        "Final remarks: I thank you for the opportunity of this test, I had a lot of fun as I learned, thank you.")
                .termsOfServiceUrl("http://localhost:8080")
                .licenseUrl("http://localhost:8080")
                .version("2.0")
                .build();
    }
}
