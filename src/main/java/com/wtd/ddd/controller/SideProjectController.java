package com.wtd.ddd.controller;

import com.google.gson.Gson;
import com.wtd.ddd.domain.SideProjectDAO;
import com.wtd.ddd.domain.SideProjectPost;
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
    private SideProjectDAO sideProjectDAO;

    @PostMapping("/post")
    public String add(@RequestBody SideProjectPost post) {
        log.error(post.toString());
        sideProjectDAO.insert(post);
        return "SUCCESS";
    }

     @GetMapping("/posts/{seq}")
     @ResponseBody
     public String get(@PathVariable int seq) {
        log.error("seq= " + seq);
        List<SideProjectPost> posts = sideProjectDAO.select(seq);
        return new Gson().toJson(posts);
     }

}
