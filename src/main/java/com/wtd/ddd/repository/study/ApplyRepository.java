package com.wtd.ddd.repository.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.ApplyCount;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Created By mand2 on 2020-10-25.
 */
public interface ApplyRepository {

    //지원하기
    Apply save(Apply apply);

    //지원상태 수정
    void update(Apply apply);

    //나의 지원한 스터디 리스트 보기
    List<Apply> findAll(Id<User, Long> userId);

    // 해당모집글에 지원한 지원자 리스트 보기
    List<Apply> findByPostId(Id<Post, Long> postId);

    // 해당 지원글 보기
    Optional<Apply> findByApplyId(Id<Apply, Long> applyId);

    // 해당모집글에 지원한 지원자 수 보기
    ApplyCount countByPostId(Id<Post, Long> postId);
}
