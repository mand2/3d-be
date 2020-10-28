package com.wtd.ddd.controller.study;

import com.wtd.ddd.controller.ApiResult;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wtd.ddd.controller.ApiResult.OK;

/**
 * Created By mand2 on 2020-10-24.
 */
@RestController
@RequestMapping("api/study")
public class StudyController {

    private final StudyService studyService;

    @Autowired
    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    //모집글 쓰기
    @PostMapping("/post")
    public ApiResult<Post> posting(@RequestBody StudyPostRequest postRequest) {
        return OK(studyService.writePost(postRequest.newPost()));
    }

    //모집글 수정
    @PutMapping("/post/{postSeq}")
    public ApiResult<Post> editPost(@RequestBody StudyPostRequest postRequest, @PathVariable Long postSeq) {
        return OK(studyService.modifyPost(postRequest.newPost(postSeq)));
    }

    //모집글 삭제
    @DeleteMapping("/post/{postSeq}")
    public ApiResult<Id<Post, Long>> deletePost(@RequestBody StudyPostRequest postRequest, @PathVariable Long postSeq) {
        return OK(studyService.deletePost(postRequest.newPost(postSeq)));
    }

    //모집글 다건조회-리스트
    @GetMapping("/post")
    public ApiResult<StudyPostResponse> getList(@ModelAttribute Pageable pageable,
                                                @RequestParam(required = false, defaultValue = "") String title,
                                                @RequestParam(required = false, defaultValue = "") String placeSeq,
                                                @RequestParam(required = false, defaultValue = "") String statusSeq) {

        Long offset = pageable.offset();
        int limit = pageable.limit();
        return OK(studyService.posts(
                title, Id.of(StudyCode.class, placeSeq), Id.of(StudyCode.class, statusSeq),
                offset, limit));
    }

    //모집글 단건조회-자세히보기
    @GetMapping("/post/{postSeq}")
    public ApiResult<StudyPostResponse> view(@PathVariable Long postSeq) {
        return OK(studyService.findPost(Id.of(Post.class, postSeq)));
    }

    //지원하기
    @PostMapping("/apply/{postSeq}")
    public ApiResult<Apply> applying(@RequestBody StudyPostRequest postRequest, @PathVariable Long postSeq) {
        return OK(studyService.apply(postRequest.newApply(postSeq)));
    }

    //지원 결과안내 (수락/거절) 혹은 지원취소
    @PutMapping("/apply/status/{applySeq}")
    public ApiResult<Apply> changeApplyStatus(@PathVariable Long applySeq, @RequestBody StudyPostRequest postRequest){
        return OK(studyService.applyModify(
                Id.of(Apply.class, applySeq),
                Id.of(StudyCode.class, postRequest.getApplyStatus())));
    }
}
