package com.wtd.ddd.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SideProjectPost {

    private int seq;
    private String leader;
    private String meeting;
    private String location;
    private String status;
    private int memTotalCapa;
    private String memInfoSeq;

    private String title;
    private String contents;

    private String deleteYn;
    private Date createDt;

    @Override
    public String toString() {
        return "SideProjectPost{" +
                "seq=" + seq +
                ", leader='" + leader + '\'' +
                ", meeting='" + meeting + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", memTotalCapa=" + memTotalCapa +
                ", memInfoSeq=" + memInfoSeq +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", deleteYn='" + deleteYn + '\'' +
                ", createDt=" + createDt +
                '}';
    }
}
