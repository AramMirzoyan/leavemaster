package com.leave.master.leavemaster.security.properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leave.master.leavemaster.dto.auth.LoginRequestDto;
import com.leave.master.leavemaster.dto.auth.TokenResponseDto;
import com.leave.master.leavemaster.security.model.KcTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Set;

import static com.leave.master.leavemaster.security.UserInfoConstant.PREFERRED_USERNAME;
import static com.leave.master.leavemaster.security.UserInfoConstant.SUB;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApiClientProperties {

    @Lazy
    private final HttpHeaders headers;

    public HttpEntity<MultiValueMap<String, String>> requestFromSource(final LoginRequestDto source) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", source.getEmail());
        params.add("password", source.getPassword());
        params.add(OAuth2Constants.CLIENT_ID, source.getClientId());
        params.add(OAuth2Constants.GRANT_TYPE, "password");
        params.add(OAuth2Constants.CLIENT_SECRET, source.getClientSecret());
        return new HttpEntity<>(params, headers);
    }

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

    private DecodedJWT decodedJWTFromBody(final String accessToken) {
        return JWT.decode(accessToken);
    }
}
