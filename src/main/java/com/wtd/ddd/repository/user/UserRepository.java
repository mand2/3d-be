package com.wtd.ddd.repository.user;

import com.wtd.ddd.controller.user.UserExistResponse;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.user.User;

import java.util.Optional;

/**
 * Created By mand2 on 2020-10-25.
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(Id<User, Long> userId);

    UserExistResponse findByUserID(Id<User, Long> userId);

}
