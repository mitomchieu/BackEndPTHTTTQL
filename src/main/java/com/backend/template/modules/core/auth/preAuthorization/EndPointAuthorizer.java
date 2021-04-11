package com.backend.template.modules.core.auth.preAuthorization;

import com.backend.template.base.common.ConstSetting.ERoles;
import com.backend.template.base.common.exception.BackendError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service("EndPointAuthorizer")
@Slf4j
public class EndPointAuthorizer {
    public boolean authorizer(ERoles[] roles) throws BackendError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BackendError(HttpStatus.UNAUTHORIZED,  "Not allow");
        }

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        if (roles.length == 0 ) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(ERoles.GUEST)) {
                    return false;
                }
            }
            return true;
        }
        for (GrantedAuthority authority : authorities) {
            for (int i = 0; i < roles.length; i++) {
                if(authority.getAuthority().equals(roles[i].toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
