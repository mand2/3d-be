package com.wtd.ddd.controller.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wtd.ddd.model.user.Email;
import com.wtd.ddd.model.user.User;
import lombok.Data;

/**
 * Created By mand2 on 2020-10-29.
 */
@Data
public class UserJoinRequest {
    private Long userId;
    private String name;
    private String email;

    public User newUser() {
        return new User(userId, name, new Email(email));
    }

}
