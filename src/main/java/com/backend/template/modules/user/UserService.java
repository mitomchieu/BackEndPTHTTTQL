package com.backend.template.modules.user;

import com.backend.template.modules.user.model.User;
import com.backend.template.modules.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
