package pl.markopolo.matchpredictor.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static pl.markopolo.matchpredictor.swagger.SwaggerConstants.*;


@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(singletonList(securityContext()))
                .securitySchemes(singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .tags(new Tag(USERS_API_TAG, "APIs related to users"));
    }

    private ApiKey apiKey() {
        return new ApiKey(API_KEY_REFERENCE, AUTHORIZATION, "header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(securityReference()).build();
    }

    private List<SecurityReference> securityReference() {
        AuthorizationScope[] authorizationScope = {
                new springfox.documentation.service.AuthorizationScope(AUTHORIZATION_SCOPE, AUTHORIZATION_DESCRIPTION)};
        return Collections.singletonList(new SecurityReference(API_KEY_REFERENCE, authorizationScope));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(API_TITLE, getApiDescription(), API_VERSION, API_TERMS_OF_SERVICE_URL,
                contact(), API_LICENSE, API_LICENSE_URL, emptyList());
    }

    private Contact contact() {
        return new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL);
    }
}