package com.wtd.ddd.repository.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.study.Writer;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

import static com.wtd.ddd.util.DateTimeUtils.dateTimeOf;

/**
 * Created By mand2 on 2020-10-25.
 */
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Post save(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO STUDY_POSTS(seq, title, content, writer, member_number, status_seq, place_seq, created_at) " +
                "values(null,?,?,?,?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query, new String[]{"seq"});
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setLong(3, post.getWriter().getUserId().value());
            ps.setInt(4, post.getMemberNumber());
            ps.setString(5, post.getStatusSeq().value());
            ps.setString(6, post.getPlaceSeq().value());
            ps.setTimestamp(7, DateTimeUtils.timestampOf(post.getCreatedAt()));
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        long generatedSeq = key != null ? key.longValue() : -1;
        return new Post.Builder(post).seq(generatedSeq).build();
    }

    @Override
    public void update(Post post) {
        String query = "UPDATE STUDY_POSTS " +
                "SET " +
                "title=?, " +
                "content=?, " +
                "member_number=?, " +
                "status_seq=?, " +
                "place_seq=? " +
                "WHERE seq=? ";
        jdbcTemplate.update(query,
                post.getTitle(), post.getContent(), post.getMemberNumber(),
                post.getStatusSeq().value(), post.getPlaceSeq().value(), post.getSeq());
    }

    @Override
    public void delete(Post post) {
        String query = "UPDATE STUDY_POSTS " +
                "SET " +
                "delete_flag=true " +
                "WHERE seq=? ";
        jdbcTemplate.update(query, post.getSeq());
    }

    // TODO dynamic query 테스트 필요.
    @Override
    public List<Post> findAll(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                              Long offset, int limit) {
        ArrayList args = new ArrayList();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" p.SEQ, ");
        sb.append(" p.TITLE, ");
        sb.append(" p.CONTENT, ");
        sb.append(" p.PLACE_SEQ, ");
        sb.append("FROM STUDY_POSTS p ");
        sb.append("WHERE p.DELETE_FLAG=false ");

        if (null != title && !"".equals(title)) {
            sb.append("AND p.title LIKE ? ");
            args.add("%" + title + "%");
        }
        if (null != placeId && !"".equals(placeId.value())) {
            sb.append("AND p.place_seq=? ");
            args.add(placeId.value());
        }
        if (null != statusId && !"".equals(statusId.value())) {
            sb.append("AND p.status_seq=? ");
            args.add(statusId.value());
        }
        sb.append("limit ?,?");

        return jdbcTemplate.query(sb.toString(), new Object[]{args, offset, limit}, mapper);
    }

    @Override
    public int findAllCount(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId) {
        ArrayList args = new ArrayList();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(p.SEQ)");
        sb.append("FROM STUDY_POSTS p ");
        sb.append("WHERE p.DELETE_FLAG=false ");

        if (null != title && !"".equals(title)) {
            sb.append("AND p.title LIKE ? ");
            args.add("%" + title + "%");
        }
        if (null != placeId && !"".equals(placeId.value())) {
            sb.append("AND p.place_seq=? ");
            args.add(placeId.value());
        }
        if (null != statusId && !"".equals(statusId.value())) {
            sb.append("AND p.status_seq=? ");
            args.add(statusId.value());
        }
        return jdbcTemplate.queryForObject(sb.toString(), new Object[]{args}, Integer.class);
    }

    @Override
    public Optional<Post> findById(Id<Post, Long> postId) {
        String query = "SELECT " +
                "   p.SEQ, " +
                "   p.TITLE, " +
                "   p.CONTENT, " +
                "   u.SEQ  as writer_id, " +
                "   u.NAME as writer_name, " +
                "   p.MEMBER_NUMBER, " +
                "   p.STATUS_SEQ, " +
                "   p.PLACE_SEQ, " +
                "   p.SUBJECT_SEQ, " +
                "   p.CREATED_AT " +
                "from STUDY_POSTS p " +
                "inner join USERS u on u.SEQ = p.WRITER " +
                "where p.SEQ=?";
        List<Post> results = jdbcTemplate.query(query, new Object[]{postId.value()}, mapper);

        return Optional.ofNullable(results.isEmpty()?null:results.get(0));
    }


    static RowMapper<Post> mapper = (rs, rowNum) -> new Post.Builder()
            .seq(rs.getLong("seq"))
            .title(rs.getString("title"))
            .content(rs.getString("content"))
            .writer(new Writer(Id.of(User.class, rs.getLong("writer_id")), rs.getString("writer_name")))
            .memberNumber(rs.getInt("member_number"))
            .statusSeq(Id.of(StudyCode.class, rs.getString("status_seq")))
            .placeSeq(Id.of(StudyCode.class, rs.getString("place_seq")))
            .subject(rs.getString("subject_seq"))
            .createdAt(dateTimeOf(rs.getTimestamp("created_at")))
            .build();
}
