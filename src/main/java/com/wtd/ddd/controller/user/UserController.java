package com.wtd.ddd.controller.user;

import com.wtd.ddd.controller.ApiResult;
import com.wtd.ddd.controller.study.Pageable;
import com.wtd.ddd.controller.study.StudyPostResponse;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.service.StudyService;
import com.wtd.ddd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wtd.ddd.controller.ApiResult.OK;

/**
 * Created By mand2 on 2020-10-29.
 * 회원-이용지계정. 관리자계정X
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final StudyService studyService;

    @Autowired
    public UserController(UserService userService, StudyService studyService) {
        this.userService = userService;
        this.studyService = studyService;
    }

    //가입
    @PostMapping("/join")
    public ApiResult<Long> joining(@RequestBody UserJoinRequest joinRequest) {
        /*name(github user_id), email*/
        return OK(userService.join(joinRequest.newUser()));
    }

    @GetMapping("/mypage/{seq}")
    public ApiResult<User> mypage(@PathVariable Long seq) {
        return OK(userService.myInfo(Id.of(User.class, seq)));
    }


    // 마이페이지-내가작성한 스터디모집글 list보기 >해당 글의 post_seq 누르면 자세히보기 api호출.
    @GetMapping("/mypage/{userSeq}/post")
    public ApiResult<StudyPostResponse> myStudyPosts(@ModelAttribute Pageable pageable,
                                                     @PathVariable Long userSeq) {
        Long offset = pageable.offset();
        int limit = pageable.limit();
        return OK(studyService.posts(Id.of(User.class, userSeq), offset, limit));
    }

    // 마이페이지-내가지원한 스터디모집글 list보기
    @GetMapping("/mypage/{userSeq}/post/apply")
    public ApiResult<StudyPostResponse> myAppliedStudyPosts(@ModelAttribute Pageable pageable,
                                                            @PathVariable Long userSeq) {
        Long offset = pageable.offset();
        int limit = pageable.limit();
        return OK(studyService.appliedPosts(Id.of(User.class, userSeq), offset, limit));
    }

    // 마이페이지-내가작성한 스터디지원글 list보기
    @GetMapping("/mypage/{userSeq}/apply")
    public ApiResult<List<Apply>> myApplies(@PathVariable Long userSeq) {
        return OK(studyService.applies(Id.of(User.class, userSeq)));
    }

}
