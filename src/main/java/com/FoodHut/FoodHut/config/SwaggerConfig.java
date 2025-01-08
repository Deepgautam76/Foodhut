package com.FoodHut.FoodHut.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * This is the swagger config
 * For generating the documentation
 * Of the api end-point in a project
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("My Food hut projects APIs")
                        .version("v1.0")
                        .description("project API end-point documents")
                );
    }

}
