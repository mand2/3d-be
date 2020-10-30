package com.wtd.ddd.repository.sideprj;

import com.wtd.ddd.domain.SideProjectRecArea;
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

    public List<SideProjectRecArea> selectBySeq(int recSeq) {
        String query = "SELECT * from side_project_rec_area where seq = " + recSeq;
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

    public int updateCapacity(int recSeq) {
        String query = "UPDATE side_project_rec_area SET fixed_capa = fixed_capa + 1 WHERE seq = ? ";
        return jdbcTemplate.update(query, recSeq);
    }

    public int updateToFinish(int applySeq) {
        String query = "UPDATE side_project_rec_area SET finish_yn = 'Y' WHERE seq = ( \n"
                + " select a.seq from side_project_rec_area a, side_project_post b, side_project_apply c \n" +
                "                                              where c.post_seq = b.seq and b.seq = a.post_seq and c.seq =" + applySeq + ")";
        return jdbcTemplate.update(query, applySeq);
    }

    public int findRecAreaSeq(int postSeq, String recArea) {
        String query = "select seq\n" +
                "from side_project_rec_area\n" +
                "where post_seq =" + postSeq + " and area = \'" + recArea + "\'";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

}
