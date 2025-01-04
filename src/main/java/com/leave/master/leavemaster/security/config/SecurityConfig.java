package com.leave.master.leavemaster.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.leave.master.leavemaster.security.converter.JwtAuthConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Security configuration for the application.
 *
 * <p>This class configures security features such as authentication, authorization, and CORS.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
public class SecurityConfig {
  private static final String[] WHITE_LIST_URLS = {
    "/auth/login",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui/index.html",
    "/swagger-ui.html",
    "/webjars/**",
    "/swagger-resources/**",
    "/swagger-resources",
    "/swagger-resources/**",
    "/actuator/**",
  };

  private static final long CORS_MAX_AGE = 3600L; // Max age for CORS preflight requests
  private final JwtAuthConverter jwtAuthConverter;

  @Value("${web.cors.origins}")
  private String allowedOrigins;

  @Value("${server.servlet.context-path}")
  private String ctxPath;

  /**
   * Configures the security filter chain for the application.
   *
   * <p>This includes disabling CSRF, setting session management to stateless, and enabling OAuth2
   * resource server support.
   *
   * @param http the {@link HttpSecurity} to configure.
   * @return the configured {@link SecurityFilterChain}.
   * @throws Exception if an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            req -> {
              req.requestMatchers(WHITE_LIST_URLS).permitAll();
              req.requestMatchers(ctxPath + "/**").hasAnyRole("").anyRequest().authenticated();
            })
        .httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
        .sessionManagement(ss -> ss.sessionCreationPolicy(STATELESS))
        .build();
  }

  /**
   * Provides a password encoder bean using BCrypt hashing algorithm.
   *
   * @return a {@link PasswordEncoder} instance.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the CORS settings for the application.
   *
   * <p>This allows all methods and headers from the origins defined in the application properties.
   *
   * @return the {@link CorsConfigurationSource} for CORS settings.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(allowedOrigins));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setMaxAge(CORS_MAX_AGE);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
