package com.leave.master.leavemaster.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class for setting up API documentation using OpenAPI and Swagger.
 *
 * <p>This class is designed to provide API documentation configuration for the application,
 * including properties and security settings.
 */
@Configuration
@PropertySource("classpath:api-doc.properties")
@EnableConfigurationProperties
public class ApiDocumentationConfig {

  /**
   * Provides properties for API documentation configuration.
   *
   * @return an instance of {@link OpenAPIProperties} populated with configuration properties.
   */
  @Bean
  @ConfigurationProperties(prefix = "api-doc")
  public OpenAPIProperties openAPIProperties() {
    return new OpenAPIProperties();
  }

  /**
   * Configures the OpenAPI instance with security and metadata information.
   *
   * @param properties
   * @return an {@link OpenAPI} instance configured with security requirements and basic info.
   */
  @Bean
  public OpenAPI openAPI(OpenAPIProperties properties) {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authorization"))
        .components(
            new Components().addSecuritySchemes("Bearer Authorization", createAPIKeyScheme()))
        .info(
            new Info()
                .title(properties.getTitle())
                .version(properties.getVersion())
                .description(properties.getDescription())
                .contact(new Contact().name("Contact to leave master application development team"))
                .license(new License().name("Leave Master License")))
        .addServersItem(
            new Server()
                .url("http://localhost:8015/leavemaster-app-api/v1")
                .description("Local development server"));
  }

  /**
   * Creates a security scheme for API authentication.
   *
   * @return a {@link SecurityScheme} configured for HTTP Bearer authentication.
   */
  private static SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
  }

  /**
   * Properties class to hold API documentation configuration.
   *
   * <p>This class is a container for API documentation properties, such as title, description, and
   * version.
   */
  @Setter
  @Getter
  public static class OpenAPIProperties {

    private String title;
    private String description;
    private String version;
  }
}
