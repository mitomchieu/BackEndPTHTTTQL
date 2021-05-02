package com.backend.template.modules.core.user;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.modules.core.user.model.QUser;
import com.backend.template.modules.core.user.model.User;
import com.backend.template.modules.core.user.repository.UserRepository;
import com.querydsl.core.types.dsl.PathBuilder;
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
        super(QUser.user, User.class.getName());
        entityPathBuilder = new PathBuilder<>(this.modelClass, "user");
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepo.save(user);
        } catch (Exception ex) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Duplicate username");
        }
        user.setPassword(null);
        return user;
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
