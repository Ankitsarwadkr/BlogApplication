package com.example.BlogApplication.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI BlogAppOpenApi()
    {
        final String JwtSchemeName="bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("BlogApp Aoi Documentation")
                        .description("Interactive documentation for BlogApp APIs with Jwt login and optional OAuth2 login note")
                )
                .addSecurityItem(new SecurityRequirement().addList(JwtSchemeName))
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes(JwtSchemeName, new SecurityScheme()
                                        .name(JwtSchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                )
                );
    }

}
