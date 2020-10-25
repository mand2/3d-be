package com.wtd.ddd.repository.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.ApplyCount;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
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

import static com.wtd.ddd.util.DateTimeUtils.dateTimeOf;

/**
 * Created By mand2 on 2020-10-25.
 */
@Repository
public class ApplyRepositoryImpl implements ApplyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApplyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Apply save(Apply apply) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO STUDY_APPLIES(seq, post_seq, apply_user, content, apply_status) " +
                "values (null,?,?,?,?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"seq"});
            ps.setLong(1, apply.getPostSeq());
            ps.setLong(2, apply.getApplyUser().value());
            ps.setString(3, apply.getContent());
            ps.setString(4, apply.getApplyStatus().value());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        long generatedKey = key != null ? key.longValue() : -1;

        return new Apply.Builder(apply).seq(generatedKey).build();
    }

    @Override
    public void update(Apply apply) {
        String query = "UPDATE STUDY_APPLIES " +
                "SET " +
                "APPLY_STATUS=? " +
                "WHERE seq=?";
        jdbcTemplate.update(query,
                apply.getApplyStatus().value(), apply.getSeq());
    }

    //empty list 가능성 있음.
    //나의 지원한 스터디 리스트 보기
    @Override
    public List<Apply> findAll(Id<User, Long> userId) {
        String query = "SELECT " +
                "    SEQ as apply_seq, " +
                "    APPLY_USER as apply_user, " +
                "    APPLY_STATUS as apply_status " +
                "from STUDY_APPLIES " +
                "where APPLY_USER=?";

        return jdbcTemplate.query(query, new Object[]{userId.value()}, mapper);
    }

    // 해당모집글에 지원한 지원자 리스트 보기
    @Override
    public List<Apply> findByPostId(Id<Post, Long> postId) {
        String query = "SELECT " +
                "    SEQ as apply_seq, " +
                "    APPLY_USER as apply_user, " +
                "    APPLY_STATUS as apply_status " +
                "from STUDY_APPLIES " +
                "where POST_SEQ=?";

        return jdbcTemplate.query(query, new Object[]{postId.value()}, mapper);
    }

    @Override
    public ApplyCount countByPostId(Id<Post, Long> postId) {
        String query = "SELECT " +
                "   p.MEMBER_NUMBER, " +
                "    sum(case when a.APPLY_STATUS = 'SS10' then true else false end) as SS10 , " +
                "    sum(case when a.APPLY_STATUS = 'SS20' then true else false end) as SS20 , " +
                "    sum(case when a.APPLY_STATUS = 'SS30' then true else false end) as SS30 , " +
                "    sum(case when a.APPLY_STATUS = 'SS40' then true else false end) as SS40 " +
                "from STUDY_POSTS p " +
                "left outer join STUDY_APPLIES a on a.POST_SEQ = p.SEQ " +
                "where p.SEQ=?";
        return jdbcTemplate.queryForObject(query, cntMapper, postId.value());
    }

    static RowMapper<Apply> mapper = (rs, rowNum) -> new Apply.Builder()
            .seq(rs.getLong("seq"))
            .postSeq(rs.getLong("post_seq"))
            .applyUser(Id.of(User.class, rs.getLong("apply_user")))
            .content(rs.getString("content"))
            .applyStatus(Id.of(StudyCode.class, rs.getString("apply_status")))
            .createdAt(dateTimeOf(rs.getTimestamp("created_at")))
            .build();

    static RowMapper<ApplyCount> cntMapper = (rs, rowNum) ->
            new ApplyCount(
                    rs.getInt("MEMBER_NUMBER"),
                    rs.getInt("SS10"),
                    rs.getInt("SS20"),
                    rs.getInt("SS30"),
                    rs.getInt("SS40")
            );

}
