package com.wtd.ddd.model.user;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Created By mand2 on 2020-10-24.
 */
public class User {
    private final Long seq;
    private String name; //github nickname. => 고칠 수 있다.
    private Email email; //github email. => 고칠 수 있다.
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private int loginCount;
    private boolean deleteFlag;

    public User(String name, Email email) {
        this(null, name, email, null, null, 0, false);
    }

    public User(Long seq, String name, Email email, LocalDateTime createdAt, LocalDateTime lastLoginAt, int loginCount, boolean deleteFlag) {
        this.seq = seq;
        this.name = name;
        this.email = email;
        this.createdAt = defaultIfNull(createdAt, now());
        this.lastLoginAt = lastLoginAt;
        this.loginCount = loginCount;
        this.deleteFlag = deleteFlag;
    }

    public Long getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(seq, user.seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("name", name)
                .append("email", email)
                .append("createdAt", createdAt)
                .append("lastLoginAt", lastLoginAt)
                .append("loginCount", loginCount)
                .append("deleteFlag", deleteFlag)
                .toString();
    }

    static public class Builder {
        private Long seq;
        private String name;
        private Email email;
        private LocalDateTime createdAt;
        private LocalDateTime lastLoginAt;
        private int loginCount;
        private boolean deleteFlag;

        public Builder() {
        }

        public Builder(User user) {
            this.seq = user.seq;
            this.name = user.name;
            this.email = user.email;
            this.createdAt = user.createdAt;
            this.lastLoginAt = user.lastLoginAt;
            this.loginCount = user.loginCount;
            this.deleteFlag = user.deleteFlag;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(Email email) {
            this.email = email;
            return this;
        }
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }
        public Builder loginCount(int loginCount) {
            this.loginCount = loginCount;
            return this;
        }
        public Builder deleteFlag(boolean deleteFlag) {
            this.deleteFlag = deleteFlag;
            return this;
        }

        public User build() {
            return new User(seq, name, email, createdAt, lastLoginAt, loginCount, deleteFlag);
        }
    }
}
