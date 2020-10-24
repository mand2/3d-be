package com.wtd.ddd.controller;

import com.wtd.ddd.domain.SideProjectDAO;
import com.wtd.ddd.domain.SideProjectPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SideProjectController {

    @Autowired
    private SideProjectDAO sideProjectDAO;

    @PostMapping("/api/sideprj/post")
    public String add(@RequestBody SideProjectPost post) {
        System.out.print(post.toString());
        sideProjectDAO.insert(post);
        return "SUCCESS";
    }

}
