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
    public String add(@RequestBody SideProjectPostRequest request) {
        // TODO : 인원수 validation, 코드 정리
        log.error("INPUT:" + request.toString());
        SideProjectPost post = SideProjectPostRequest.convertToPost(request);
        int key = sideProjectPostDAO.insert(post);
        List<SideProjectRecArea> areas = SideProjectPostRequest.convertToRecArea(request, key);
        sideProjectService.addRecAreas(areas);
        return "SUCCESS";
    }

     @GetMapping("/posts/{seq}")
     @ResponseBody
     public String get(@PathVariable int seq) {
        //TODO 모집 분야별 정보, 인원도 가져와야 함
        List<SideProjectPost> posts = sideProjectPostDAO.select(seq);
        return new Gson().toJson(posts);
     }

    @GetMapping("/recs/")
    @ResponseBody
    public String get() {
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.select();
        return new Gson().toJson(areas);
    }

}
