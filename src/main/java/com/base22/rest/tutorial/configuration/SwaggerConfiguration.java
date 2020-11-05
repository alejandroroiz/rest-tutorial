package com.base22.rest.tutorial.configuration;

import com.google.common.base.Predicates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  private static final Logger logger = LogManager.getLogger();

  @Bean
  public Docket api() {

    logger.info("Registering Swagger2 documentation");
    Docket docket =
        new Docket(DocumentationType.SWAGGER_2).host("localhost:8080").select().apis(RequestHandlerSelectors.any())
            .paths(Predicates.not(PathSelectors.regex("/error.*"))).build().securitySchemes(Arrays.asList(apiKey()))
            .securityContexts(Collections.singletonList(securityContext())).apiInfo(apiInfo());

    logger.info("Swagger2 documentation registered correctly");
    return docket;
  }

  private ApiInfo apiInfo() {


    return new ApiInfoBuilder().title("Base22 Springboot Rest tutorial")
        .description("Rest service written in spring boot").version("1.0.0").build();
  }

  private ApiKey apiKey() {
    return new ApiKey("Bearer", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
    return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
  }
}
