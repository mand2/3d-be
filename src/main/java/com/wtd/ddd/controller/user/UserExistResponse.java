package com.wtd.ddd.controller.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wtd.ddd.model.user.User;
import lombok.Data;

/**
 * Created By mand2 on 2020-10-30.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserExistResponse {

    private boolean exist;
    private Long seq;
    private String name;


    public UserExistResponse(boolean exist) {
        this.exist = exist;
    }

    public UserExistResponse(boolean exist, User user) {
        this.exist = exist;
        this.seq = user.getSeq();
        this.name = user.getName();
    }
}
