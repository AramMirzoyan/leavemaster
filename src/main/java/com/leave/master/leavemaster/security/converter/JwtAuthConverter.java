package com.leave.master.leavemaster.security.converter;

import static com.leave.master.leavemaster.security.UserInfoConstant.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
  private final LeaveMasterSecurityProperties leaveMasterSecurityProperties;

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    Collection<GrantedAuthority> authorities =
        Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractResRoles(jwt).stream())
            .collect(Collectors.toSet());
    return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
  }

  private String getPrincipalClaimName(@NonNull Jwt jwt) {
    String claimName = JwtClaimNames.SUB;
    if (jwt.getClaim(PREFERRED_USERNAME.getName()) != null) {
      claimName = PREFERRED_USERNAME.getName();
    }

    return jwt.getClaim(claimName);
  }

  @SuppressWarnings("unchecked")
  private Collection<? extends GrantedAuthority> extractResRoles(@NonNull Jwt jwt) {
    Map<String, Object> resourceAccess;
    Map<String, Object> resource;
    Collection<String> resourceRoles;

    if (jwt.getClaim(RESOURCE_ACCESS.getName()) == null) {
      return Collections.EMPTY_SET;
    }

    resourceAccess = jwt.getClaim(RESOURCE_ACCESS.getName());
    if (resourceAccess.get(leaveMasterSecurityProperties.getKeycloak().getClientSecret()) == null) {
      return Collections.EMPTY_SET;
    }

    resource =
        (Map<String, Object>)
            resourceAccess.get(leaveMasterSecurityProperties.getKeycloak().getClientSecret());

    resourceRoles = (Collection<String>) resource.get(ROLES.getName());

    return resourceRoles.stream()
        .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX.getName() + role))
        .collect(Collectors.toSet());
  }
}
