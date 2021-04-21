package com.backend.template.modules.core.auth;

import com.backend.template.base.common.ConstSetting.ERoles;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.modules.core.auth.dto.LoginRequestDTO;
import com.backend.template.modules.core.auth.dto.LoginResponseDTO;
import com.backend.template.modules.core.auth.model.Role;
import com.backend.template.modules.core.auth.repository.RoleRepositoty;
import com.backend.template.modules.core.auth.token.provider.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping(path = "auth")
@ApiCommonResponse
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private RoleRepositoty roleRepositoty;

    public  static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        Role admin = new Role(ERoles.ADMIN);
        Role user = new Role(ERoles.USER);
        Role guest = new Role(ERoles.GUEST);
        roleRepositoty.save(admin);
        roleRepositoty.save(user);
        roleRepositoty.save(guest);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO user) throws BackendError{
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
        loginResponse.setAccess_token(jwt);
        return ResponseTool.POST_OK(loginResponse);
    }
}
