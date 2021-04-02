package com.backend.template.modules.user;

import javax.validation.Valid;
import com.backend.template.common.response.ResponseTool;
import com.backend.template.common.response.model.APIResponse;
import com.backend.template.modules.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody User user) throws Exception {
        return ResponseTool.POST_OK(this.userService.createUser(user));
    }
}
