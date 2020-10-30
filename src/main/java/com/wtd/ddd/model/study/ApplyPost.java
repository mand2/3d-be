package com.wtd.ddd.model.study;

import com.wtd.ddd.model.commons.Id;

/**
 * Created By mand2 on 2020-10-30.
 * 지원한 모집글의 title, subject, status만.
 */
public class ApplyPost {
    private final String title;
    private final Id<StudyCode, String> statusSeq;

    public ApplyPost(String title, Id<StudyCode, String> statusSeq) {
        this.title = title;
        this.statusSeq = statusSeq;
    }
}
