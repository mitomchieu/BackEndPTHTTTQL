package com.backend.template.modules.auth;

import com.backend.template.common.response.ResponseTool;
import com.backend.template.common.response.model.APIResponse;
import com.backend.template.modules.auth.dto.LoginResponseDTO;
import com.backend.template.modules.auth.token.provider.JwtProvider;
import com.backend.template.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(path = "auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider tokenProvider;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> login(@RequestBody User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setJwt(jwt);
        return ResponseTool.POST_OK(loginResponse);
    }
}
