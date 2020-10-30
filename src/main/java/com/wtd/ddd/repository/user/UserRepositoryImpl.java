package com.wtd.ddd.repository.user;

import com.wtd.ddd.controller.user.UserExistResponse;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.user.Email;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.wtd.ddd.util.DateTimeUtils.dateTimeOf;

/**
 * Created By mand2 on 2020-10-25.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO USERS(seq, user_id, email, name, created_at) " +
                "values (null,?,?,?,?)";

        // 깃헙은 user_id 와 name을 동일하게 세팅한다. name으로 디스플레이 함.
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query, new String[]{"seq"});
            ps.setLong(1, user.getUserId());
            ps.setString(2, user.getEmail().getAddress());
            ps.setString(3, user.getName());
            ps.setTimestamp(4, DateTimeUtils.timestampOf(user.getCreatedAt()));
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        long generatedSeq = key != null ? key.longValue() : -1;
        return new User.Builder(user).seq(generatedSeq).build();
    }

    @Override
    public Optional<User> findById(Id<User, Long> userId) {
        String query = "SELECT " +
                " seq, " +
                " user_id, " +
                " email, " +
                " name, " +
                " created_at " +
                "FROM USERS " +
                "WHERE SEQ=?";

        List<User> results = jdbcTemplate.query(query, new Object[]{userId.value()}, mapper);
        return Optional.ofNullable(results.isEmpty()?null:results.get(0));
    }

    @Override
    public UserExistResponse findByUserID(Id<User, Long> userId) {
        // 유저존재하는지 검색 -> 없으면 false, 있으면 true.
        String query = "SELECT " +
                " seq, " +
                " user_id, " +
                " email, " +
                " name, " +
                " created_at " +
                "FROM USERS " +
                "WHERE USER_ID=?";

        List<User> results = jdbcTemplate.query(query, new Object[]{userId.value()}, mapper);

        return results.isEmpty()
                ? new UserExistResponse(false)
                : new UserExistResponse(true, results.get(0));
    }

    static RowMapper<User> mapper = (rs, rowNum) -> new User.Builder()
            .seq(rs.getLong("seq"))
            .userId(rs.getLong("user_id"))
            .email(new Email(rs.getString("email")))
            .name(rs.getString("name"))
            .createdAt(dateTimeOf(rs.getTimestamp("created_at")))
            .build();


}
