package com.wtd.ddd.controller.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wtd.ddd.model.study.Apply;
import com.wtd.ddd.model.study.ApplyCount;
import com.wtd.ddd.model.study.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created By mand2 on 2020-10-26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyPostResponse {

    // 다건조회
    private List<Post> posts; //TODO posts non-null인지..
    private int totalCount;

    // 단건조회
    private List<Apply> applies;
    private ApplyCount applyCount;
    private Post post;


    public StudyPostResponse(List<Post> posts, int totalCount) {
        this.posts = posts;
        this.totalCount = totalCount;
    }

    public StudyPostResponse(List<Apply> applies, ApplyCount applyCount, Post post) {
        this.applies = applies;
        this.applyCount = applyCount;
        this.post = post;
    }
}
