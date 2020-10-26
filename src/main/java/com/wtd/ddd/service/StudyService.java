package com.wtd.ddd.service;

import com.google.common.base.Preconditions;
import com.wtd.ddd.error.NotFoundException;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.ApplyCount;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.user.User;
import com.wtd.ddd.repository.study.ApplyRepositoryImpl;
import com.wtd.ddd.repository.study.PostRepository;
import com.wtd.ddd.repository.study.PostRepositoryImpl;
import com.wtd.ddd.util.studycode.StatusUtils;
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
    public Id<Post, Long> deletePost(Post post) {
        postRepository.delete(post);
        return Id.of(Post.class, post.getSeq());
    }

    //리스트
    @Transactional(readOnly = true)
    public List<Post> posts(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                            Long offset, int limit) {
        // list
        // total count
        return postRepository.findAll(title, placeId, statusId, offset, limit);
    }

    //자세히

    // postId로 post 단건 찾기
//    @Transactional(readOnly = true)
    private Optional<Post> findPost(Id<Post, Long> postId) {
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
    public Apply applyModify(Id<Apply, Long> applyId, Id<StudyCode, String> applyStatus) {
        // seq로 가져온다.
        return applyRepository.findByApplyId(applyId).map(apply -> {
            // 대기중 일때만 수정 가능.
            if (StatusUtils.WAITING.getCodeSeq().equals(apply.getApplyStatus().value())) {
                // 취소 || 거절
                denyOrCancel(applyStatus, apply);
                //수락
                accept(applyStatus, apply);
            }
            return apply;
        }).orElseThrow(() -> new NotFoundException(Apply.class, Apply.class, Id.of(Apply.class, applyId)));
    }

    // 지원 거절 혹은 취소
    private void denyOrCancel(Id<StudyCode, String> applyStatus, Apply apply) {
        if (StatusUtils.DENIED.getCodeSeq().equals(applyStatus.value())
            || StatusUtils.CANCEL.getCodeSeq().equals(applyStatus.value())) {
            applyRepository.update(apply);
        }
    }

    //수락 -> 수락전, count 비교해서 수락가능. && 수락인원이 이 사람으로 인해 다 차면 post 또한 마감 ..?
    private void accept(Id<StudyCode, String> applyStatus, Apply apply) {
        if (StatusUtils.ACCEPTED.getCodeSeq().equals(applyStatus.value())) {
            ApplyCount applyCount = applicantCount(Id.of(Post.class, apply.getPostSeq()));
            if (applyCount.getAcceptCount() < applyCount.getMemberNumber()) {
                applyRepository.update(apply);
            }
        }
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
