package com.leave.master.leavemaster.security.properties;

import static com.leave.master.leavemaster.security.UserInfoConstant.PREFERRED_USERNAME;
import static com.leave.master.leavemaster.security.UserInfoConstant.SUB;

import java.util.Set;

import org.keycloak.OAuth2Constants;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.model.KcTokenResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Component class for managing API client properties and related operations.
 *
 * <p>Provides methods for constructing HTTP requests and processing token responses.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ApiClientProperties {

  @Lazy private final HttpHeaders headers;

  /**
   * Creates an HTTP request entity from the given login request source.
   *
   * <p>Subclasses can override this method to customize the request construction logic.
   *
   * @param source the {@link LoginRequestDto} containing login request details.
   * @return an {@link HttpEntity} containing the request parameters and headers.
   */
  public HttpEntity<MultiValueMap<String, String>> requestFromSource(final LoginRequestDto source) {
    final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("username", source.getEmail());
    params.add("password", source.getPassword());
    params.add(OAuth2Constants.CLIENT_ID, source.getClientId());
    params.add(OAuth2Constants.GRANT_TYPE, "password");
    params.add(OAuth2Constants.CLIENT_SECRET, source.getClientSecret());
    return new HttpEntity<>(params, headers);
  }

  /**
   * Constructs a {@link TokenResponseDto} from the given token response and roles.
   *
   * <p>Subclasses can override this method to customize token response processing.
   *
   * @param tokenBody the {@link KcTokenResponse} containing token details.
   * @param roles a set of roles associated with the token.
   * @return a {@link TokenResponseDto} containing processed token details.
   */
  public TokenResponseDto tokenResponseDto(final KcTokenResponse tokenBody, Set<String> roles) {
    var jwt = decodedJWTFromBody(tokenBody.accessToken());
    return TokenResponseDto.builder()
        .userId(jwt.getClaim(SUB.getName()).asString())
        .username(jwt.getClaim(PREFERRED_USERNAME.getName()).asString())
        .accessToken(tokenBody.accessToken())
        .refreshToken(tokenBody.refreshToken())
        .accessTokenExpiresIn(tokenBody.accessTokenExpiresIn())
        .refreshTokenExpiresIn(tokenBody.refreshTokenExpiresIn())
        .roles(roles)
        .build();
  }

  /**
   * Decodes a JWT access token.
   *
   * <p>This method is private and intended for internal use only.
   *
   * @param accessToken the access token to decode.
   * @return a {@link DecodedJWT} instance.
   */
  private DecodedJWT decodedJWTFromBody(final String accessToken) {
    return JWT.decode(accessToken);
  }
}
