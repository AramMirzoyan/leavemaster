package com.leave.master.leavemaster.service.external;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.time.Duration;

/** Configuration class for setting up external API clients with retry and timeout settings. */
@EnableRetry
@Configuration
public class ExternalApiClientConfig {
  private static final long BACK_OFF_PERIOD = 5_000L;
  private static final int SECOND = 3;
  private static final int MAX_ATTEMPTS = 3;
  private static final long CONNECT_TIMEOUT = 5_000L;
  private static final long READ_TIMEOUT = 15_000L;

  /**
   * Creates a RetryTemplate for external API interactions. Configures a fixed backoff policy with
   * retries for {@link SocketTimeoutException}.
   *
   * @return Configured {@link RetryTemplate} instance.
   */
  @Bean
  public RetryTemplate externalApiRetryTemplate() {
    final var fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(BACK_OFF_PERIOD);
    return new RetryTemplateBuilder()
        .fixedBackoff(Duration.ofSeconds(SECOND))
        .customPolicy(
            new SimpleRetryPolicy(
                MAX_ATTEMPTS,
                new BinaryExceptionClassifier(false) {
                  @Override
                  public Boolean classify(Throwable classifiable) {
                    if (classifiable instanceof ResourceAccessException resourceAccessException) {
                      return resourceAccessException.getCause() instanceof SocketTimeoutException;
                    }
                    return false;
                  }
                }))
        .build();
  }

  /**
   * Creates and configures a {@link RestTemplate} bean for external API communication. Sets
   * connection and read timeouts.
   *
   * @param builder the {@link RestTemplateBuilder} used to build the {@link RestTemplate}.
   * @return a configured {@link RestTemplate} instance.
   */
  @Bean
  public RestTemplate externalApiRestTemplate(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
        .setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
        .build();
  }
}
