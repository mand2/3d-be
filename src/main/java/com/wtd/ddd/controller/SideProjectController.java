package com.wtd.ddd.controller;

import com.google.gson.Gson;
import com.wtd.ddd.domain.SideProjectRecArea;
import com.wtd.ddd.repository.SideProjectPostDAO;
import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.repository.SideProjectRecAreaDAO;
import com.wtd.ddd.service.SideProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/sideprj")
public class SideProjectController {

    @Autowired
    private SideProjectPostDAO sideProjectPostDAO;

    @Autowired
    private SideProjectRecAreaDAO sideProjectRecAreaDAO;

    @Autowired
    private SideProjectService sideProjectService;

    @PostMapping("/post")
    public String addPost(@RequestBody SideProjectPostRequest request) {
        log.error("INPUT:" + request.toString());
        sideProjectService.writePost(request);
        return "SUCCESS";
    }

     @GetMapping("/posts/{seq}")
     @ResponseBody
     public String getPost(@PathVariable int seq) {
        SideProjectPostResponse response = sideProjectService.get(seq);
        return new Gson().toJson(response);
     }

    @GetMapping("/posts/")
    @ResponseBody
    public String getPosts() {
        List<SideProjectPost> posts = sideProjectPostDAO.selectAll();
        return new Gson().toJson(posts);
    }



    @GetMapping("/recs/")
    @ResponseBody
    public String get() {
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.select();
        return new Gson().toJson(areas);
    }

}
