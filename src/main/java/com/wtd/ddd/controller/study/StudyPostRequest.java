package com.wtd.ddd.controller.study;

import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.Post;
import com.wtd.ddd.model.study.StudyCode;
import com.wtd.ddd.model.study.Writer;
import com.wtd.ddd.model.user.User;
import lombok.Data;

/**
 * Created By mand2 on 2020-10-26.
 */
@Data
public class StudyPostRequest {

    //TODO null 값 어떻게 되는지 확인 필요.

    private Long postSeq;
    private Long userId;

    private String title;
    private String content;
    private int memberNumber;

    private String statusSeq;
    private String placeSeq;
    private String subject;

    private String applyStatus;

    public Post newPost() {
        return new Post(this.title, this.content, new Writer(Id.of(User.class, this.userId), null),
                this.memberNumber, Id.of(StudyCode.class, this.placeSeq), this.subject);
    }

    public Post newPost(Long postSeq) {
        return new Post(postSeq, this.title, this.content, null,
                this.memberNumber, Id.of(StudyCode.class, this.placeSeq), this.subject);
    }

    public Apply newApply() {
        return new Apply(this.postSeq, Id.of(User.class, this.userId), this.content);
    }

    public Apply newApply(Long postSeq) {
        return new Apply(postSeq, Id.of(User.class, this.userId), this.content);
    }

}
