package com.backend.template.modules.core.auth.preAuthorization;

import java.util.Collection;
import java.util.Objects;

import com.backend.template.base.common.ConstSetting.ERoles;
import com.backend.template.base.common.exception.BackendError;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("EndPointAuthorizer")
@SuppressWarnings("unchecked")
public class EndPointAuthorizer {
    public boolean authorizer(ERoles[] roles) throws BackendError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BackendError(HttpStatus.UNAUTHORIZED,  "Not allow");
        }

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        if (roles.length == 0 ) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(ERoles.GUEST.toString())) {
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
