package com.wtd.ddd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wtd.ddd.controller.study.StudyPostRequest;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.user.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created By mand2 on 2020-10-27.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class StudyServiceTest extends TestCase {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudyService studyService;

    private Id<Post, Long> postId;
    private Id<Apply, Long> applyId;
    private Id<User, Long> writerId;
    private Id<User, Long> applierId;

    @Before
    public void setUp() throws Exception {
        writerId = Id.of(User.class, 5L);
        applierId = Id.of(User.class, 1L);
        postId = Id.of(Post.class, 2L);
        //3은 확정됨. 4는 대기중.
        applyId = Id.of(Apply.class, 4L);
    }

    // 스터디모집글_작성
    @Test
    public void testWritePost() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        StudyPostRequest req = new StudyPostRequest();
        req.setTitle("스프링 시큐리티 공부하실분");
        req.setContent("5명 건대에서 주말에 3시간씩 하실분?");
        req.setUserId(writerId.value());
        req.setMemberNumber(5);
        req.setPlaceSeq("SP12");
        req.setSubjectSeq("스프링");
        logger.info("req : {}", mapper.writeValueAsString(req));

        Post post = studyService.writePost(req.newPost());
//        logger.info("post : {}", mapper.writeValueAsString(post));
//        logger.info("post : {}", new Gson().toJson(post));
        logger.info("post : {}", post);
    }

    // 스터디모집글_수정
    public void testModifyPost() {
    }

    // 스터디모집글_삭제
    public void testDeletePost() {
    }

    // 스터디모집글_리스트
    public void testPosts() {
    }

    // 스터디모집글_자세히
    public void testFindPost() {
    }

    // 스터디모집글_지원하기
    public void testApply() {
    }

    // 스터디모집글_지원상태수정
    public void testApplyModify() {
    }

    // 스터디모집글_지원리스트보기(나의)
    public void testApplies() {
    }
}