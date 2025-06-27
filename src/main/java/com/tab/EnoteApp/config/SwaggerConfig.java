package com.tab.EnoteApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        OpenAPI openAPI = new OpenAPI();

        Info info = new Info();
        info.setTitle("Enotes Api");
        info.setDescription("Enotes Api");
        info.setVersion("1.0.0");
        info.setTermsOfService("http://tabrezklizos.com");
        info.setContact(new Contact().name("Tabrez").email("tabrez@klizos.com").url("http://tabrezklizos.com"));
        info.setLicense(new License().name("klizos").url("https://github.com/klizos"));

        List<Server> serverList = List.of
                (new Server().description("dev").url("http://localhost:8080"),
                new Server().description("test").url("http://localhost:8081"),
                new Server().description("prod").url("http://localhost:8082"));

        SecurityScheme securityScheme = new SecurityScheme().name("Authorization")
                 .scheme("bearer").type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT").in(SecurityScheme.In.HEADER);

        Components component = new Components().addSecuritySchemes("Token", securityScheme);

        openAPI.servers(serverList);
        openAPI.info(info);
        openAPI.setComponents(component);
        openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));

        return openAPI;
    }

}
