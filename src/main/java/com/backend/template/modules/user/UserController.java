package com.backend.template.modules.user;

import javax.annotation.Resource;

import com.backend.template.modules.user.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(final @RequestBody User user) {
        System.out.print("debug");
        return new ResponseEntity<User>(this.userService.createUser(user), HttpStatus.ACCEPTED);
    }
}
