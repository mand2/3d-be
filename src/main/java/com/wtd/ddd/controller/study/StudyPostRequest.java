package com.wtd.ddd.controller.study;

import lombok.Data;

/**
 * Created By mand2 on 2020-10-26.
 */
@Data
public class StudyPostRequest {

    private Long postSeq;
    private Long userId;

    private String title;
    private String content;
    private int memberNumber;

    private String statusSeq;
    private String placeSeq;
    private String subject;
}
