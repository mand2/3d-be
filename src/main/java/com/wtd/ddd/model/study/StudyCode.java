package com.wtd.ddd.model.study;

import com.wtd.ddd.util.studycode.StatusUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Created By mand2 on 2020-10-25.
 * study system code
 */
public class StudyCode {
    private final String groupSeq;
    private final String groupDesc;
    private String codeSeq;
    private String codeDesc;
    private boolean enable;

    public StudyCode(StatusUtils status) {
        this.groupSeq = status.getGroupSeq();
        this.groupDesc = status.getGroupDescription();
        this.codeSeq = status.getCodeSeq();
        this.codeDesc = status.getCodeDescription();
        this.enable = status.isEnable();
    }

    public StudyCode(String groupSeq, String groupDesc, String codeSeq, String codeDesc, boolean enable) {
        this.groupSeq = groupSeq;
        this.groupDesc = groupDesc;
        this.codeSeq = codeSeq;
        this.codeDesc = codeDesc;
        this.enable = enable;
    }

    public String getGroupSeq() {
        return groupSeq;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public String getCodeSeq() {
        return codeSeq;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeSeq);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        StudyCode studyCode = (StudyCode) o;
        return Objects.equals(codeSeq, studyCode.codeSeq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupSeq", groupSeq)
                .append("groupDesc", groupDesc)
                .append("codeSeq", codeSeq)
                .append("codeDesc", codeDesc)
                .append("enable", enable)
                .toString();
    }
}
