package com.wtd.ddd.repository.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.study.Writer;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Post save(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String query = "INSERT INTO STUDY_POSTS(seq, title, content, writer, member_number, status_seq, place_seq, subject_seq, created_at) " +
                "values(null,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(query, new String[]{"seq"});
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setLong(3, post.getWriter().getUserId().value());
            ps.setInt(4, post.getMemberNumber());
            ps.setString(5, post.getStatusSeq().value());
            ps.setString(6, post.getPlaceSeq().value());
            ps.setString(7, post.getSubjectSeq());
            ps.setTimestamp(8, DateTimeUtils.timestampOf(post.getCreatedAt()));
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
                "place_seq=?, " +
                "subject_seq=?, " +
                "updated_at=curtime() " +
                "WHERE seq=? ";
        jdbcTemplate.update(query,
                post.getTitle(), post.getContent(), post.getMemberNumber(),
                post.getStatusSeq().value(), post.getPlaceSeq().value(), post.getSubjectSeq(), post.getSeq());
    }

    @Override
    public void delete(Post post) {
        String query = "UPDATE STUDY_POSTS " +
                "SET " +
                "delete_flag=true " +
                "WHERE seq=? ";
        jdbcTemplate.update(query, post.getSeq());
    }

    @Override
    public List<Post> findAll(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                              Long offset, int limit) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" SEQ, ");
        sb.append(" TITLE, ");
        sb.append(" CONTENT, ");
        sb.append(" PLACE_SEQ ");
        sb.append("FROM STUDY_POSTS ");
        sb.append("WHERE DELETE_FLAG=false ");

        if (null != title && !"".equals(title)) {
            sb.append("AND title LIKE :title ");
            params.put("title", "%"+title+"%");
        }
        if (null != placeId && !"".equals(placeId.value())) {
            sb.append("AND place_seq=:place_seq ");
            params.put("place_seq", placeId.value());
        }
        if (null != statusId && !"".equals(statusId.value())) {
            sb.append("AND status_seq=:status_seq ");
            params.put("status_seq", statusId.value());
        }
        sb.append("limit :offset , :limit");
        params.put("offset", offset);
        params.put("limit", limit);

        return namedParameterJdbcTemplate.query(sb.toString(), params, mapper2);
    }

    @Override
    public int findAllCount(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(p.SEQ)");
        sb.append("FROM STUDY_POSTS p ");
        sb.append("WHERE p.DELETE_FLAG=false ");

        if (null != title && !"".equals(title)) {
            sb.append("AND title LIKE :title ");
            params.put("title", "%"+title+"%");
        }
        if (null != placeId && !"".equals(placeId.value())) {
            sb.append("AND place_seq=:place_seq ");
            params.put("place_seq", placeId.value());
        }
        if (null != statusId && !"".equals(statusId.value())) {
            sb.append("AND status_seq=:status_seq ");
            params.put("status_seq", statusId.value());
        }

        return namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
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


    //스터디작성자-> 모집글리스트 보기
    @Override
    public List<Post> findAllByUserId(Id<User, Long> userId, Long offset, int limit) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" SEQ, ");
        sb.append(" TITLE, ");
        sb.append(" CONTENT, ");
        sb.append(" PLACE_SEQ ");
        sb.append("FROM STUDY_POSTS ");
        sb.append("WHERE DELETE_FLAG=false ");
        sb.append("AND WRITER=? ");
        sb.append("limit ?,? ");

        return jdbcTemplate.query(sb.toString(), new Object[]{userId.value(), offset, limit}, mapper2);
    }


    @Override
    public List<Post> findAllByApplierId(Id<User, Long> userId, Long offset, int limit) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" p.SEQ, ");
        sb.append(" p.TITLE, ");
        sb.append(" p.CONTENT, ");
        sb.append(" p.PLACE_SEQ ");
        sb.append("FROM STUDY_APPLIES a ");
        sb.append("RIGHT OUTER JOIN STUDY_POSTS p ");
        sb.append("ON p.seq = a.post_seq ");
        sb.append("WHERE a.apply_user=? ");
        sb.append("limit ?,? ");

        return jdbcTemplate.query(sb.toString(), new Object[]{userId.value(), offset, limit}, mapper2);
    }

    static RowMapper<Post> mapper = (rs, rowNum) -> new Post.Builder()
            .seq(rs.getLong("seq"))
            .title(rs.getString("title"))
            .content(rs.getString("content"))
            .writer(new Writer(Id.of(User.class, rs.getLong("writer_id")), rs.getString("writer_name")))
            .memberNumber(rs.getInt("member_number"))
            .statusSeq(Id.of(StudyCode.class, rs.getString("status_seq")))
            .placeSeq(Id.of(StudyCode.class, rs.getString("place_seq")))
            .subjectSeq(rs.getString("subject_seq"))
            .createdAt(dateTimeOf(rs.getTimestamp("created_at")))
            .build();

    static RowMapper<Post> mapper2 = (rs, rowNum) -> new Post.Builder()
            .seq(rs.getLong("seq"))
            .title(rs.getString("title"))
            .content(rs.getString("content"))
            .placeSeq(Id.of(StudyCode.class, rs.getString("place_seq")))
            .build();
}
