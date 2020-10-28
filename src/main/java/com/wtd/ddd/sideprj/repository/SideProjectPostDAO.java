package com.wtd.ddd.sideprj.repository;

import com.wtd.ddd.sideprj.web.SideProjectMyApplyResponse;
import com.wtd.ddd.sideprj.domain.SideProjectPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Slf4j
public class SideProjectPostDAO {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public SideProjectPostDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        this.
        simpleJdbcInsert
                .withTableName("side_project_post")
                .usingGeneratedKeyColumns("seq")
                .usingColumns("leader","meeting","location","mem_total_capa","title","contents");
    }

    public int insert(SideProjectPost post) {
        SqlParameterSource params = new MapSqlParameterSource()
                                    .addValue("leader", post.getLeader())
                                    .addValue("meeting", post.getMeeting())
                                    .addValue("location", post.getLocation())
                                    .addValue("mem_total_capa", post.getMemTotalCapa())
                                    .addValue("title", post.getTitle())
                                    .addValue("contents", post.getContents());
        Number number = simpleJdbcInsert.executeAndReturnKey(params);
        return number.intValue();
    }

    public List<SideProjectPost> select(int seq) {
        String query = "SELECT * from side_project_post where seq = " + seq;
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectPost>(SideProjectPost.class));
    }

    public List<SideProjectPost> selectAll(String title, Integer limit, Integer offset) {
        String query = "SELECT * from side_project_post";

        if (!StringUtils.isEmpty(title)) {
            query += "  WHERE title like \'%" + title + "%\'";
        }

        if (limit != null && offset != null) {
            query += " LIMIT " + limit + " OFFSET " + offset;
        }

        List<SideProjectPost> posts = jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectPost>(SideProjectPost.class));
        log.error(posts.toString());
        return posts;
    }

    public int getTotalCount(String title) {
        String query = "SELECT count(*) FROM side_project_post";
        if (!StringUtils.isEmpty(title)) {
            query += "  WHERE title like \'%" + title + "%\'";
        }
        int totalCount = jdbcTemplate.queryForObject(query, Integer.class);
        return totalCount;
    }

    public List<SideProjectPost> selectByLeaderMemId(String memId) {
        String query = "select * from side_project_post where leader = \'" + memId + "\'";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectPost>(SideProjectPost.class));
    }

    public int updateCapacity(int applySeq) {
        String query = "UPDATE side_project_post SET mem_capa = mem_capa + 1 WHERE seq = ( \n"
                + " select a.post_seq from side_project_apply a where a.seq = ? )";
        return jdbcTemplate.update(query, applySeq);
    }

    public List<SideProjectPost> selectByApplySeq(int applySeq) {
        String query = "SELECT * from side_project_post where seq = ( \n"
                + " select post_seq from side_project_apply where seq = " + applySeq + " )";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectPost>(SideProjectPost.class));
    }

    public int updateToFinish(int applySeq) {
        String query = "UPDATE side_project_post SET finish_yn = 'Y' WHERE seq = ( \n"
                + " select post_seq from side_project_apply where seq = " + applySeq +  ") ";
        return jdbcTemplate.update(query, applySeq);
    }


}
