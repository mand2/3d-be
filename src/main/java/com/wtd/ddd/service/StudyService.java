package com.wtd.ddd.service;

import com.google.common.base.Preconditions;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.ApplyCount;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.repository.study.ApplyRepositoryImpl;
import com.wtd.ddd.repository.study.PostRepository;
import com.wtd.ddd.repository.study.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created By mand2 on 2020-10-26.
 */
@Service
public class StudyService {

    private final PostRepositoryImpl postRepository;
    private final ApplyRepositoryImpl applyRepository;

    @Autowired
    public StudyService(PostRepositoryImpl postRepository, ApplyRepositoryImpl applyRepository) {
        this.postRepository = postRepository;
        this.applyRepository = applyRepository;
    }

    //쓰기
    @Transactional
    public Post writePost(Post post) {
        return postRepository.save(post);
    }

    //수정
    @Transactional
    public Post modifyPost(Post post) {
        postRepository.update(post);
        return post;
    }

    //삭제
    @Transactional
    public Post deletePost(Post post) {
        postRepository.delete(post);
        return post;
    }

    //리스트
    @Transactional(readOnly = true)
    public List<Post> posts(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                            Long offset, int limit) {
        return postRepository.findAll(title, placeId, statusId, offset, limit);
    }

    //자세히
    @Transactional(readOnly = true)
    public Optional<Post> findById(Id<Post, Long> postId) {
        checkNotNull(postId, "postId must be provided");
        return postRepository.findById(postId);
    }

    //지원하기
    @Transactional
    public Apply apply(Apply apply) {
        return applyRepository.save(apply);
    }

    //지원 상태 수정
    @Transactional
    public Apply applyModify(Apply apply) {
        applyRepository.update(apply);
        return apply;
    }

    //지원 리스트 보기(지원자기준)
    @Transactional(readOnly = true)
    public List<Apply> applies(Id<User, Long> userId) {
        checkNotNull(userId, "userId must be provided");
        return applyRepository.findAll(userId);
    }

    // 모집글 지원자 보기
    @Transactional(readOnly = true)
    public List<Apply> applicantList(Id<Post, Long> postId) {
        checkNotNull(postId, "postId must be provided");
        return applyRepository.findByPostId(postId);
    }

    // 모집글 지원자 수
    @Transactional(readOnly = true)
    public ApplyCount applicantCount(Id<Post, Long> postId) {
        checkNotNull(postId, "postId must be provided");
        return applyRepository.countByPostId(postId);
    }

}
