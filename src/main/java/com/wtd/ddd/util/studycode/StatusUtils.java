package com.wtd.ddd.util.studycode;

import lombok.Getter;

/**
 * Created By mand2 on 2020-10-25.
 * 스터디 지원, 모집 상태코드
 */
@Getter
public enum StatusUtils {
    WAITING("SS", "", "SS10", "대기중", true),
    ACCEPTED("SS", "", "SS20", "참여", true),
    DENIED("SS", "", "SS30", "거절됨", true),
    CANCLE("SS", "", "SS30", "지원취소", true),
    OPEN("SS", "", "SSP1", "모집중", true),
    CLOSE("SS", "", "SSP2","모집완료", true);

    private String groupSeq;
    private String groupDescription;
    private String codeSeq;
    private String codeDescription;
    private boolean enable;

    StatusUtils(String groupSeq, String groupDescription, String codeSeq, String codeDescription, boolean enable) {
        this.groupSeq = groupSeq;
        this.groupDescription = groupDescription;
        this.codeSeq = codeSeq;
        this.codeDescription = codeDescription;
        this.enable = enable;
    }

}
