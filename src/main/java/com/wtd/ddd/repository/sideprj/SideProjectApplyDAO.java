package com.wtd.ddd.repository.sideprj;

import com.wtd.ddd.web.SideProjectApplyToMeResponse;
import com.wtd.ddd.web.SideProjectMyApplyResponse;
import com.wtd.ddd.domain.SideProjectApply;
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
                .usingColumns("post_seq","rec_area_seq","mem_id","rec_area","message");
    }

    public int insert(SideProjectApply apply) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("post_seq", apply.getPostSeq())
                .addValue("rec_area_seq", apply.getRecAreaSeq())
                .addValue("mem_id", apply.getMemId())
                .addValue("rec_area", apply.getRecArea())
                .addValue("message", apply.getMessage());
        Number number = simpleJdbcInsert.executeAndReturnKey(params);
        return number.intValue();
    }

    public List<SideProjectMyApplyResponse> selectByApplicantsMemId(String memId) {
        String query = "select a.seq AS apply_seq, a.apply_stat AS my_status, b.status AS prj_status, b.mem_capa, b.mem_total_capa,\n" +
                "        b.title, b.create_dt\n" +
                " from side_project_apply a, side_project_post b where a.post_seq = b.seq " +
                " and a.mem_id = \'" + memId + "\' and a.cancel_yn = 'N' ";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectMyApplyResponse>(SideProjectMyApplyResponse.class));
    }

    public int update(SideProjectApply apply) {
        String query = "UPDATE side_project_apply SET apply_stat = ? WHERE seq = ?";
        return jdbcTemplate.update(query, apply.getApplyStat(), apply.getSeq());
    }

    public int updateToCancel(int applySeq) {
        String query = "UPDATE side_project_apply SET cancel_yn = 'Y' WHERE seq = ?";
        return jdbcTemplate.update(query, applySeq);
    }

    public List<SideProjectApplyToMeResponse> selectAppliesByPostSeq(int postSeq) {
        String query = "select a.seq as apply_seq, b.seq as rec_seq, b.area as rec_area, a.message from side_project_apply a, side_project_rec_area b\n" +
                "where a.post_seq = " + postSeq + "  and a.post_seq = b.post_seq and a.cancel_yn = 'N' and a.rec_area_seq = b.seq";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<SideProjectApplyToMeResponse>(SideProjectApplyToMeResponse.class));
    }


}
