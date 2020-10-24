package com.wtd.ddd.model.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Created By mand2 on 2020-10-24.
 */
public class Writer {
    private final Id<User, Long> userId;
    private final String name;

    public Writer(Id<User, Long> userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Id<User, Long> getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return Objects.equals(userId, writer.userId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("userId", userId)
                .append("name", name)
                .toString();
    }
}
