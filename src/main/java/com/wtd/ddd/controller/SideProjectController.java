package com.wtd.ddd.controller;

import com.google.gson.Gson;
import com.wtd.ddd.domain.SideProjectApply;
import com.wtd.ddd.domain.SideProjectRecArea;
import com.wtd.ddd.repository.SideProjectApplyDAO;
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
    private SideProjectApplyDAO sideProjectApplyDAO;

    @Autowired
    private SideProjectService sideProjectService;

    @PostMapping("/post")
    @ResponseBody
    public String addPost(@RequestBody SideProjectPostRequest request) {
        log.error("Request:" + request.toString());
        sideProjectService.writePost(request);
        return "등록 성공!";
    }

     @GetMapping("/posts/{seq}")
     @ResponseBody
     public String getPost(@PathVariable int seq) {
        SideProjectPostResponse response = sideProjectService.get(seq);
        return new Gson().toJson(response);
     }

    @GetMapping("/posts")
    @ResponseBody
    public String getPosts(@RequestParam(value = "search", required = false) String title,
                           @RequestParam(value = "limit", required = false) Integer limit,
                           @RequestParam(value = "offset", required = false) Integer offset) {
        int totalCount = sideProjectPostDAO.getTotalCount(title);
        List<SideProjectPost> posts = sideProjectPostDAO.selectAll(title, limit, offset);
        SideProjectPostSearchResponse response = SideProjectPostSearchResponse.builder()
                                                                                .totalCount(totalCount)
                                                                                .posts(posts)
                                                                                .build();
        return new Gson().toJson(response);
    }

    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestBody SideProjectApplyRequest request) {
        log.error("Request:" + request.toString());
        SideProjectApply apply = SideProjectApply.convert(request);
        sideProjectApplyDAO.insert(apply);
        return "지원 성공!";
    }

    @GetMapping("/mypage/{memId}/applys/")
    @ResponseBody
    public String getMyApplies(@PathVariable String memId) {
        List<SideProjectApply> applies = sideProjectApplyDAO.selectByMemId(memId);
        return new Gson().toJson(applies);
    }


    @GetMapping("/recs/")
    @ResponseBody
    public String get() {
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.select();
        return new Gson().toJson(areas);
    }

}
