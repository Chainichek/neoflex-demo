package ru.chainichek.neoflexdemo.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String appVersion = "1.0.0";
    private static final String appDescription = "Приложение \"Калькулятор отпускных\"";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("%s v%s".formatted(appVersion, appDescription))
                        .version(appVersion)
                        .description(appDescription))
                .servers(
                        List.of(new Server().url("http://localhost:8080")
                                        .description("Local service")
                        ));
    }
}
