package com.poweroutrage.incident.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket adminConfig() {
        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant(
                "/incident-api/**")).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Incident Creation API", "Done with Java 8 and REST" +
                " Api", "1.0", "Free to use",
                new springfox.documentation.service.Contact("S Radhakrishnan"
                        , "797921", "Radhakrishnan.S3@cognizant.com"), "API " +
                "License", "localhost:8083/", Collections.emptyList());
    }
}
