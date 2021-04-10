package com.backend.template.modules.user;

import com.backend.template.common.exception.BackendError;
import com.backend.template.modules.user.model.QUser;
import com.backend.template.modules.user.model.User;
import com.backend.template.modules.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("userService")
public class UserService {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    protected JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }

    public Long countByUsername(String username) {
        QUser qUser = QUser.user;
        return getJPAQueryFactory().select(qUser.username.countDistinct()).from(qUser).fetchOne();
    }

    public User createUser(User user) throws BackendError {
        if (countByUsername(user.getUsername()) > 0) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Username have been used");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepo.save(user);
        } catch (Exception ex) {
            String message = ex.getMessage();
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        user.setPassword(null);
        return user;
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
