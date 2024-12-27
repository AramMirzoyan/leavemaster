package com.leave.master.leavemaster.security.converter;

import com.leave.master.leavemaster.config.LeaveMasterSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.leave.master.leavemaster.security.UserInfoConstant.PREFERRED_USERNAME;
import static com.leave.master.leavemaster.security.UserInfoConstant.RESOURCE_ACCESS;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
  private final LeaveMasterSecurityProperties propert;

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    Collection<GrantedAuthority> authorities =
        Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractResRoles(jwt).stream())
            .collect(Collectors.toSet());
    return new JwtAuthenticationToken(jwt, authorities,getPrincipalClaimName(jwt));
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
    final Map<String, LinkedHashMap<String, Object>> resourceAccess=  jwt.getClaim(RESOURCE_ACCESS.getName());
    Set<SimpleGrantedAuthority> accessRoles = new HashSet<>();

    Map<String, Object> realmAccess = resourceAccess.get(propert.getKeycloak().getClientId());

    final List<String> workerClientRoles = Objects.nonNull(realmAccess)
            ? (List<String>) realmAccess.get("roles")
            : Collections.emptyList();


    if (CollectionUtils.isNotEmpty(workerClientRoles)) {
      workerClientRoles.forEach(
              role -> accessRoles.add(new SimpleGrantedAuthority("ROLE_" + role)));
    }

    return accessRoles;
  }




}
