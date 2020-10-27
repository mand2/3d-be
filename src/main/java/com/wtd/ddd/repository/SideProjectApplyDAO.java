package com.wtd.ddd.repository;

import com.wtd.ddd.controller.SideProjectMyApplyResponse;
import com.wtd.ddd.domain.SideProjectApply;
import com.wtd.ddd.domain.SideProjectPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class SideProjectApplyDAO {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public SideProjectApplyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        this.
                simpleJdbcInsert
                .withTableName("side_project_apply")
                .usingGeneratedKeyColumns("seq")
                .usingColumns("post_seq","mem_id","rec_area","message");
    }

    public int insert(SideProjectApply apply) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("post_seq", apply.getPostSeq())
                .addValue("mem_id", apply.getMemId())
                .addValue("rec_area", apply.getRecArea())
                .addValue("message", apply.getMessage());
        Number number = simpleJdbcInsert.executeAndReturnKey(params);
        return number.intValue();
    }

    public List<SideProjectMyApplyResponse> selectByApplicantsMemId(String memId) {
        String query = "select a.seq, a.apply_stat AS status, b.mem_capa, b.mem_total_capa,\n" +
                "        b.title, b.create_dt\n" +
                " from side_project_apply a, side_project_post b where a.post_seq = b.seq " +
                " and a.mem_id = \'" + memId + "\'";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectMyApplyResponse>(SideProjectMyApplyResponse.class));
    }


}
