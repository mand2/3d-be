package com.wtd.ddd.controller.sideprj;

import com.google.gson.Gson;
import com.wtd.ddd.domain.SideProjectApply;
import com.wtd.ddd.repository.sideprj.SideProjectApplyDAO;
import com.wtd.ddd.repository.sideprj.SideProjectPostDAO;
import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.repository.sideprj.SideProjectRecAreaDAO;
import com.wtd.ddd.service.SideProjectService;
import com.wtd.ddd.web.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(value="/api/sideprj", produces="application/text;charset=utf8")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SideProjectController {

    private final SideProjectPostDAO sideProjectPostDAO;
    private final SideProjectRecAreaDAO sideProjectRecAreaDAO;
    private final SideProjectApplyDAO sideProjectApplyDAO;
    private final SideProjectService sideProjectService;

    public SideProjectController(SideProjectPostDAO sideProjectPostDAO, SideProjectRecAreaDAO sideProjectRecAreaDAO,
                                 SideProjectApplyDAO sideProjectApplyDAO, SideProjectService sideProjectService) {
        this.sideProjectService = sideProjectService;
        this.sideProjectApplyDAO = sideProjectApplyDAO;
        this.sideProjectRecAreaDAO = sideProjectRecAreaDAO;
        this.sideProjectPostDAO = sideProjectPostDAO;
    }

    @PostMapping("/post")
    @ResponseBody
    public String addPost(@RequestBody SideProjectPostRequest request) {
        log.info("Request:" + request.toString());
        return sideProjectService.writePost(request);
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

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        e.printStackTrace();
        return "서비스 처리 도중 오류가 발생했습니다.";
    }

}
