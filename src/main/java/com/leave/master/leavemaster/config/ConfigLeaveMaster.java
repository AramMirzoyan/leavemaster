package com.leave.master.leavemaster.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for the LeaveMaster application.
 *
 * <p>Provides beans for configuring JWT authority conversion and HTTP headers.
 *
 * <p>Designed for extension if additional beans or configurations are required.
 */
@Configuration
@RequiredArgsConstructor
public class ConfigLeaveMaster {

  private final MailConfigurationProperties mailConfigurationProperties;

  /**
   * Configures a {@link JwtGrantedAuthoritiesConverter} bean for converting JWT claims to Spring
   * Security granted authorities.
   *
   * <p>Subclasses can override this method to customize the authority conversion logic.
   *
   * @return a configured {@link JwtGrantedAuthoritiesConverter} instance.
   */
  @Bean
  public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
    return new JwtGrantedAuthoritiesConverter();
  }

  /**
   * Configures a lazy-loaded {@link HttpHeaders} bean with default content type set to {@link
   * MediaType#APPLICATION_FORM_URLENCODED}.
   *
   * <p>Subclasses can override this method to provide custom headers if needed.
   *
   * @return a configured {@link HttpHeaders} instance.
   */
  @Bean
  @Lazy
  public HttpHeaders headers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }

  /**
   * Configures and provides a {@link JavaMailSender} bean for sending emails.
   *
   * <p>Subclasses can override this method to provide a custom mail sender configuration.
   *
   * @return a configured {@link JavaMailSender} instance.
   */
  @Bean
  @Lazy
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setUsername(mailConfigurationProperties.getUsername());
    mailSender.setPassword(mailConfigurationProperties.getPassword());
    mailSender.setHost(mailConfigurationProperties.getHost());
    mailSender.setPort(mailConfigurationProperties.getPort());
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    mailSender.setJavaMailProperties(props);

    return mailSender;
  }
}
