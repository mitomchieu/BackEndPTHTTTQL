package com.backend.template.modules.auth.preAuthorization;

import com.backend.template.common.exception.BackendError;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service("EndPointAuthorizer")
@Slf4j
public class EndPointAuthorizer {
    public boolean authorizer(String[] roles) throws BackendError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new BackendError(HttpStatus.UNAUTHORIZED,  "Not allow");
        }
        System.out.println("debug");
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            for (int i = 0; i < roles.length; i++) {
                if(authority.getAuthority().equals(roles[i])) {
                    return true;
                }
            }
        }
        return false;
    }
}
