package com.backend.template.modules.auth;

import com.backend.template.common.exception.BackendError;
import com.backend.template.common.response.ResponseTool;
import com.backend.template.common.response.model.APIResponse;
import com.backend.template.modules.auth.dto.LoginResponseDTO;
import com.backend.template.modules.auth.model.Role;
import com.backend.template.modules.auth.repository.RoleRepositoty;
import com.backend.template.modules.auth.token.provider.JwtProvider;
import com.backend.template.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.catalog.Catalog;

@RestController
@RequestMapping(path = "auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private RoleRepositoty roleRepositoty;


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        Role guest = new Role("GUEST");
        roleRepositoty.save(admin);
        roleRepositoty.save(user);
        roleRepositoty.save(guest);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> login(@RequestBody User user) throws BackendError{
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception ex) {
            throw new BackendError(HttpStatus.UNAUTHORIZED, "Wrong password username");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setJwt(jwt);
        return ResponseTool.POST_OK(loginResponse);
    }
}
