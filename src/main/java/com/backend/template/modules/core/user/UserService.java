package com.backend.template.modules.core.user;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.modules.core.user.model.User;
import com.backend.template.modules.core.user.repository.UserRepository;
import com.backend.template.modules.user.model.QUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService extends BaseService<QUser> {

    UserRepository userRepo;
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(QUser.user);
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long countByUsername(String username) {
        return getJPAQueryFactory()
                    .select(qModel.username.count())
                    .where(qModel.username.eq(username))
                    .from(qModel)
                    .fetchOne();
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
