package com.wtd.ddd.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SideProjectDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert(SideProjectPost post) {
        String query = "INSERT INTO side_project_post (seq, title, contents, create_dt) values (SIDE_PROJECT_POST_SEQ.nextval, ?, ?, sysdate)";
        return jdbcTemplate.update(query, post.getTitle(), post.getContents());
    }




}
