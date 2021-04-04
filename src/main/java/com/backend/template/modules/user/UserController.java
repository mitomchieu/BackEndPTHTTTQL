package com.backend.template.modules.user;

import javax.validation.Valid;

import com.backend.template.common.response.ResponseTool;
import com.backend.template.common.response.model.APIResponse;
import com.backend.template.modules.auth.model.Role;
import com.backend.template.modules.user.dto.UserRegisterDTO;
import com.backend.template.modules.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(@Qualifier("userService") UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
    }

    @PostMapping(path = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        Role role = new Role();
        role.setName("ADMIN");
        user.setRoles(role);
        return ResponseTool.POST_OK(this.userService.createUser(user));
    }

    @GetMapping(path = "view-some-thing", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> someThing() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return ResponseTool.GET_OK("Success");
    }

}
