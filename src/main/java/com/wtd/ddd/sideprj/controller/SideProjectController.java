package com.wtd.ddd.sideprj.controller;

import com.google.gson.Gson;
import com.wtd.ddd.Application;
import com.wtd.ddd.sideprj.domain.SideProjectApply;
import com.wtd.ddd.sideprj.domain.SideProjectRecArea;
import com.wtd.ddd.sideprj.repository.SideProjectApplyDAO;
import com.wtd.ddd.sideprj.repository.SideProjectPostDAO;
import com.wtd.ddd.sideprj.domain.SideProjectPost;
import com.wtd.ddd.sideprj.repository.SideProjectRecAreaDAO;
import com.wtd.ddd.sideprj.service.SideProjectService;
import com.wtd.ddd.sideprj.web.*;
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
        sideProjectService.addApply(request);
        return "지원 성공!";
    }

    @GetMapping("/mypage/{memId}/applies")
    @ResponseBody
    public String getMyApplies(@PathVariable String memId) {
        List<SideProjectMyApplyResponse> applies = sideProjectApplyDAO.selectByApplicantsMemId(memId);
        return new Gson().toJson(applies);
    }

    @GetMapping("/mypage/{memId}/recruiting")
    @ResponseBody
    public String getMyRecruiting(@PathVariable String memId) {
        List<SideProjectPost> applies = sideProjectPostDAO.selectByLeaderMemId(memId);
        return new Gson().toJson(applies);
    }

    @GetMapping("/mypage/{memId}/recruiting/{postSeq}")
    @ResponseBody
    public String getAppliesToMyRecruiting(@PathVariable String memId, @PathVariable int postSeq) {
        List<SideProjectApplyToMeResponse> applies = sideProjectApplyDAO.selectAppliesByPostSeq(postSeq);
        return new Gson().toJson(applies);
    }

    @PostMapping("/mypage/recruiting/update")
    @ResponseBody
    public String changeApplyStatus(@RequestBody SideProjectStatusChangeRequest request) {
        if (!SideProjectApply.validate(request)) {
            return "유효하지 않은 값";
        }
        SideProjectApply apply = SideProjectApply.convertToChangeStatus(request);
        return sideProjectService.changeApplyStatus(apply) ? "변경 성공" : "변경 실패";
    }

    @GetMapping("/mypage/apply/{applySeq}/cancel}")
    @ResponseBody
    public String cancelApply(int applySeq){
        sideProjectApplyDAO.updateToCancel(applySeq);
        return "취소되었습니다";
    }


    @GetMapping("/recs/")
    @ResponseBody
    public String get() {
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.select();
        return new Gson().toJson(areas);
    }

}
