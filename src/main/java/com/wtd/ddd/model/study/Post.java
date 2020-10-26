package com.wtd.ddd.model.study;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wtd.ddd.model.commons.Id;
import com.wtd.ddd.util.studycode.StatusUtils;
import javafx.geometry.Pos;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Created By mand2 on 2020-10-24.
 */
public class Post {

    private final Long seq;
    private String title;
    private String content;

    private final Writer writer;
    private int memberNumber;

    private Id<StudyCode, String> statusSeq;
    private Id<StudyCode, String> placeSeq;
    private String subject; // 추후 List<>으로 변경.

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleteFlag;

    public Post(String title, String content, Writer writer,
                int memberNumber, Id<StudyCode, String> placeSeq, String subject) {
        this(null, title, content, writer, memberNumber,
                null, placeSeq, subject, null, null, false);
    }

    public Post(Long seq, String title, String content, Writer writer,
                int memberNumber, Id<StudyCode, String> placeSeq, String subject) {
        this(seq, title, content, writer, memberNumber,
                null, placeSeq, subject, null, null, false);
    }

    public Post(Long seq, String title, String content, Writer writer, int memberNumber,
                Id<StudyCode, String> statusSeq, Id<StudyCode, String> placeSeq, String subject,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleteFlag) {
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.memberNumber = memberNumber;
        this.statusSeq = defaultIfNull(statusSeq, Id.of(StudyCode.class, StatusUtils.OPEN.getCodeSeq()));
        this.placeSeq = placeSeq;
        this.subject = subject;
        this.createdAt = defaultIfNull(createdAt, now());
        this.updatedAt = updatedAt;
        this.deleteFlag = deleteFlag;
    }

    public void modify(String title, String content, int memberNumber,
                       Id<StudyCode, String> placeSeq, String subject) {
        this.title = title;
        this.content = content;
        this.memberNumber = memberNumber;
        this.placeSeq = placeSeq;
        this.subject = subject;
    }

    public void modify(Id<StudyCode, String> statusSeq) {
        this.statusSeq = statusSeq;
    }

    public Long getSeq() {
        return seq;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Writer getWriter() {
        return writer;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public Id<StudyCode, String> getStatusSeq() {
        return statusSeq;
    }

    public Id<StudyCode, String> getPlaceSeq() {
        return placeSeq;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(seq, post.seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("title", title)
                .append("content", content)
                .append("writer", writer)
                .append("memberNumber", memberNumber)
                .append("statusSeq", statusSeq)
                .append("placeSeq", placeSeq)
                .append("subject", subject)
                .append("createdAt", createdAt)
                .append("updatedAt", updatedAt)
                .append("deleteFlag", deleteFlag)
                .toString();
    }

    public static class Builder{
        private Long seq;
        private String title;
        private String content;
        private Writer writer;
        private int memberNumber;
        private Id<StudyCode, String> statusSeq;
        private Id<StudyCode, String> placeSeq;
        private String subject;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean deleteFlag;
        private Apply apply;


        public Builder() {
        }

        public Builder(Post post) {
            this.seq = post.seq;
            this.title = post.title;
            this.content = post.content;
            this.writer = post.writer;
            this.memberNumber = post.memberNumber;
            this.statusSeq = post.statusSeq;
            this.placeSeq = post.placeSeq;
            this.subject = post.subject;
            this.createdAt = post.createdAt;
            this.updatedAt = post.updatedAt;
            this.deleteFlag = post.deleteFlag;
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder writer(Writer writer) {
            this.writer = writer;
            return this;
        }

        public Builder memberNumber(int memberNumber) {
            this.memberNumber = memberNumber;
            return this;
        }

        public Builder statusSeq(Id<StudyCode, String> statusSeq) {
            this.statusSeq = statusSeq;
            return this;
        }

        public Builder placeSeq(Id<StudyCode, String> placeSeq) {
            this.placeSeq = placeSeq;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder deleteFlag(boolean deleteFlag) {
            this.deleteFlag = deleteFlag;
            return this;
        }

        public Post build() {
            return new Post(seq, title, content, writer, memberNumber,
                    statusSeq, placeSeq, subject, createdAt, updatedAt, deleteFlag);
        }
    }
}
