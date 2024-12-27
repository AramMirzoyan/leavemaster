package com.leave.master.leavemaster.service.external;

import com.leave.master.leavemaster.model.Holiday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ExternalHolidayApiClient {
  private final String apiKey;
  private final RestTemplate restTemplate;
  private final RetryTemplate retryTemplate;
  private final String apiUrl;
  private final String countryCode;

  /**
   * Constructor for ExternalHolidayApiClient.
   *
   * @param externalApiKey the API key for the external service.
   * @param externalApiUrl the base URL of the external API.
   * @param externalCountryCode the country code for holiday data.
   * @param externalRestTemplate the {@link RestTemplate} used for API calls.
   * @param externalRetryTemplate the {@link RetryTemplate} for retrying API requests.
   */
  public ExternalHolidayApiClient(
      @Value("${external.apiKey}") final String externalApiKey,
      @Value("${external.apiURL}") final String externalApiUrl,
      @Value("${external.countryCode}") final String externalCountryCode,
      @Qualifier("externalApiRestTemplate") final RestTemplate externalRestTemplate,
      final RetryTemplate externalRetryTemplate) {
    this.apiKey = externalApiKey;
    this.apiUrl = externalApiUrl;
    this.countryCode = externalCountryCode;
    this.restTemplate = externalRestTemplate;
    this.retryTemplate = externalRetryTemplate;
  }

  /**
   * Updates the API URL with the specified country code and year.
   *
   * @param countryCode the country code for the request.
   * @param year the year for which to retrieve holidays.
   * @return the constructed {@link URI}.
   */
  private URI updateApiUrl(final String countryCode, final int year) {
    return apiBaseUriBuilder()
        .pathSegment(String.valueOf(year))
        .pathSegment(countryCode)
        .build()
        .toUri();
  }

  /**
   * Builds the base URI components for the external API.
   *
   * @return a {@link UriComponentsBuilder} for the API base URL.
   */
  private UriComponentsBuilder apiBaseUriBuilder() {
    return UriComponentsBuilder.fromHttpUrl(this.apiUrl);
  }

  /**
   * Retrieves the list of holidays for the specified year.
   *
   * @param year the year for which to fetch holidays.
   * @return a list of {@link Holiday} objects.
   * @throws RestClientException if the API request fails after retries.
   */
  public List<Holiday> getHolidayResponse(final int year) {
    return Objects.requireNonNull(
        this.retryTemplate.execute(
            (RetryCallback<List<Holiday>, RestClientException>)
                context ->
                    this.restTemplate
                        .exchange(
                            new RequestEntity<>(
                                HttpMethod.GET, updateApiUrl(this.countryCode, year)),
                            new ParameterizedTypeReference<List<Holiday>>() {})
                        .getBody()));
  }
}
