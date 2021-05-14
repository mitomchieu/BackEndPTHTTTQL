package com.backend.template.modules.core.auth.filter;


import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.backend.template.base.common.ConstSetting.ERoles;
import com.backend.template.modules.core.auth.CustomUserDetails;
import com.backend.template.modules.core.user.model.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class GuestAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("debug filter guest");
        if (!Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user.getUsername(), null, Collections.singleton(new SimpleGrantedAuthority(ERoles.GUEST.toString())));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
