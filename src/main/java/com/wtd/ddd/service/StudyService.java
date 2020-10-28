package com.wtd.ddd.service;

import ch.qos.logback.core.status.StatusUtil;
import com.google.common.base.Preconditions;
import com.wtd.ddd.controller.study.StudyPostResponse;
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

import java.util.ArrayList;
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
    public StudyPostResponse posts(String title, Id<StudyCode, String> placeId, Id<StudyCode, String> statusId,
                                   Long offset, int limit) {
        List<Post> postList = new ArrayList<>();
        int totalCount = 0;

        postList = postRepository.findAll(title, placeId, statusId, offset, limit); // list
        totalCount = postRepository.findAllCount(title, placeId, statusId); // total count

        return new StudyPostResponse(postList, totalCount);
    }

    //자세히
    public StudyPostResponse findPost(Id<Post, Long> postId) {
//        checkNotNull(postId, "postId must be provided");

        //모집글이 있을 때에만 지원자관련 정보와 함께 보냄.
        return postRepository.findById(postId).map(post -> {
            List<Apply> applies = applyRepository.findByPostId(postId);
            ApplyCount applyCount = applyRepository.countByPostId(postId);

            return new StudyPostResponse(applies, applyCount, post);

        }).orElseThrow(() -> new NotFoundException(Post.class, Post.class, Id.of(Post.class, postId)));
    }

    // 모집글 지원자 보기
    private List<Apply> applicantList(Id<Post, Long> postId) {
        checkNotNull(postId, "postId must be provided");
        return applyRepository.findByPostId(postId);
    }

    // 모집글 지원자 수
    private ApplyCount applicantCount(Id<Post, Long> postId) {
        checkNotNull(postId, "postId must be provided");
        return applyRepository.countByPostId(postId);
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
        // 스터디모집중+지원자대기중인 상태에서만 수정가능.
        return applyRepository.findByApplyId(applyId).map(apply -> {
            if (StatusUtils.WAITING.getCodeSeq().equals(apply.getApplyStatus().value())
                    && isOpen(apply)) {
                // 취소 || 거절
                denyOrCancel(applyStatus, apply);
                //수락
                accept(applyStatus, apply);
            }
            return apply;
        }).orElseThrow(() -> new NotFoundException(Apply.class, Apply.class, Id.of(Apply.class, applyId)));
    }

    // 2. 스터디모집중인지? TODO 여기서 throw 하면 어떻게 되나?
    private boolean isOpen(Apply apply) {
        return postRepository.findById(Id.of(Post.class, apply.getPostSeq())).map(post -> {
            if (StatusUtils.OPEN.getCodeSeq().equals(post.getStatusSeq().value())) {
                return true;
            }
            return false;
        }).orElseThrow(() -> new NotFoundException(
                Post.class, Id.of(StudyCode.class, apply.getPostSeq()), Id.of(Apply.class, apply.getSeq())
        ));
    }


    // 지원 거절 혹은 취소
    private void denyOrCancel(Id<StudyCode, String> applyStatus, Apply apply) {
        if (StatusUtils.DENIED.getCodeSeq().equals(applyStatus.value())
            || StatusUtils.CANCEL.getCodeSeq().equals(applyStatus.value())) {
            apply.modify(applyStatus); //바꿔야할 상태값으로 변경
            applyRepository.update(apply);
        }
        // TODO 그 외의 경우 예외처리 -> 업데이트 안되었다는 알림.
    }

    //수락 -> 수락전, count 비교해서 수락가능. && 수락인원이 이 사람으로 인해 다 차면 post 또한 마감 ..?
    private void accept(Id<StudyCode, String> applyStatus, Apply apply) {
        if (StatusUtils.ACCEPTED.getCodeSeq().equals(applyStatus.value())) {
            ApplyCount applyCount = applicantCount(Id.of(Post.class, apply.getPostSeq()));
            if (applyCount.getAcceptCount() < applyCount.getMemberNumber()) {
                apply.modify(applyStatus); //바꿔야할 상태값으로 변경
                applyRepository.update(apply);
            }

            // TODO 예외처리..! 추가가능한 인원 다 찼을 때.
            // TODO 그 외의 경우 예외처리 -> 업데이트 안되었다는 알림.

        }
    }

    //지원 리스트 보기(지원자기준)
    @Transactional(readOnly = true)
    public List<Apply> applies(Id<User, Long> userId) {
        checkNotNull(userId, "userId must be provided");
        return applyRepository.findAll(userId);
    }
}
