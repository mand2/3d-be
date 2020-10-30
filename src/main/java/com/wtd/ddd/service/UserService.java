package com.wtd.ddd.service;

import com.wtd.ddd.error.NotFoundException;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.repository.user.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By mand2 on 2020-10-29.
 */
@Service
public class UserService {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    // 가입
    @Transactional
    public Long join(User user) {
        return userRepository.save(user).getSeq();
    }

    @Transactional(readOnly = true)
    public User myInfo(Id<User, Long> userId) {
        return userRepository.findById(userId)
                .orElseThrow( ()-> new NotFoundException(User.class, User.class,Id.of(User.class, userId)));
    }
}
