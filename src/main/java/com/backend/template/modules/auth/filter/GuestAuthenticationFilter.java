package com.backend.template.modules.auth.filter;


import com.backend.template.modules.auth.CustomUserDetails;
import com.backend.template.modules.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class GuestAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setId(ThreadLocalRandom.current().nextLong());
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "ANONYMOUS",
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
