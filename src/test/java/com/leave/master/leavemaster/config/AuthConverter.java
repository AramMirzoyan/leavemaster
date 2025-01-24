package com.leave.master.leavemaster.config;

import com.leave.master.leavemaster.containers.Constant;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.*;

import static com.leave.master.leavemaster.security.UserInfoConstant.PREFERRED_USERNAME;
import static com.leave.master.leavemaster.security.UserInfoConstant.RESOURCE_ACCESS;

public class AuthConverter {

    @SuppressWarnings("all")
    public Collection<? extends GrantedAuthority> extractResRoles(@NonNull Jwt jwt) {
        final Map<String, LinkedHashMap<String, Object>> resourceAccess =
                jwt.getClaim(RESOURCE_ACCESS.getName());
        Set<SimpleGrantedAuthority> accessRoles = new HashSet<>();

        Map<String, Object> realmAccess = resourceAccess.get(Constant.CLIENT_ID.getValue());

        final List<String> workerClientRoles =
                Objects.nonNull(realmAccess)
                        ? (List<String>) realmAccess.get("roles")
                        : Collections.emptyList();

        if (CollectionUtils.isNotEmpty(workerClientRoles)) {
            workerClientRoles.forEach(
                    role -> accessRoles.add(new SimpleGrantedAuthority("ROLE_" + role)));
        }

        return accessRoles;
    }

    public String getPrincipalClaimName(@NonNull Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (jwt.getClaim(PREFERRED_USERNAME.getName()) != null) {
            claimName = PREFERRED_USERNAME.getName();
        }

        return jwt.getClaim(claimName);
    }
}
