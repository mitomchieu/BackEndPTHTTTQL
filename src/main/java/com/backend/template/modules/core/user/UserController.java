package com.backend.template.modules.core.user;

import com.backend.template.base.common.ConstSetting.ERoles;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.annotations.auth.Authorization;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.modules.core.auth.model.Role;
import com.backend.template.modules.core.user.dto.UserRegisterDTO;
import com.backend.template.modules.core.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@ApiCommonResponse
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User register")
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        Role role = new Role();
        role.setName(ERoles.USER);
        user.setRole(role);
        return ResponseTool.POST_OK(this.userService.createUser(user));
    }


    @GetMapping(path = "my/profile", produces =  MediaType.APPLICATION_JSON_VALUE)
    @Authorization
    @Operation(description = "Get My profile, role = ADMIN, USER", summary =  "Get My")
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.getByUsername(authentication.getName());
        return ResponseTool.GET_OK(user);
    }

    @GetMapping(path =  "get-user-by-username/{username}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @Authorization
    @Operation(description =  "Get profile by id, role = ADMIN", summary = "Get by username", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN'})")
    public ResponseEntity<APIResponse> getByUsername(@PathVariable String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        User user = this.userService.getByUsername(username);
        return ResponseTool.GET_OK(user);
    }
}
