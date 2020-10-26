package com.wtd.ddd.repository;

import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.domain.SideProjectRecArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class SideProjectRecAreaDAO {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public SideProjectRecAreaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("SIDE_PROJECT_REC_AREA")
                .usingGeneratedKeyColumns("seq")
                .usingColumns("post_seq","area","max_capa");
    }

    public int insert(SideProjectRecArea recArea) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("post_seq", recArea.getPostSeq())
                .addValue("area", recArea.getArea())
                .addValue("max_capa", recArea.getMaxCapa());
        simpleJdbcInsert.executeAndReturnKey(params);
        return 0;
    }

    public List<SideProjectRecArea> selectByPostSeq(int postSeq) {
        String query = "SELECT * from side_project_rec_area where post_seq = " + postSeq + " and finish_yn = \'N\'";
        List<SideProjectRecArea> areas = jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectRecArea>(SideProjectRecArea.class));
        log.error(areas.toString());
        return areas;
    }

    public List<SideProjectRecArea> select() {
        String query = "SELECT * from side_project_rec_area";
        List<SideProjectRecArea> areas = jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectRecArea>(SideProjectRecArea.class));
        log.error(areas.toString());
        return areas;
    }

}
