package com.wtd.ddd.repository.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Created By mand2 on 2020-10-25.
 */
public interface PostRepository {
    // 스터디 모집글 쓰기
    Post save(Post post);

    // 스터디 모집글 수정
    void update(Post post);

    //스터디 모집글 삭제
    void delete(Post post);

    //스터디모집글 리스트 보기
    List<Post> findAll(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                       Long offset, int limit);

    //스터디모집글 리스트 count
    int findAllCount(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId);

    //스터디모집글 자세히 보기
    Optional<Post> findById(Id<Post, Long> postId);

    //스터디작성자-> 모집글리스트 보기
    List<Post> findAllByUserId(Id<User, Long> userId, Long offset, int limit);

    //스터디지원자 -> 스터디모집 리스트보기
    List<Post> findAllByApplierId(Id<User, Long> userId, Long offset, int limit);


}
