package com.backend.template.modules.user;

import com.backend.template.common.exception.BackendError;
import com.backend.template.modules.user.model.User;
import com.backend.template.modules.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder  passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) throws BackendError{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepo.save(user);
            user.setPassword(null);
        } catch (DataIntegrityViolationException ex) {
            String message = "Username " + user.getUsername() + " have been used";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return user;
    }
}
