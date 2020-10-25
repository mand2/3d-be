package com.wtd.ddd.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SideProjectRecArea {
    private int seq;
    private int postSeq;
    private String recArea;
    private int recCapa;
    private String finishYn;

    @Override
    public String toString() {
        return "SideProjectRecArea{" +
                "seq=" + seq +
                ", postSeq=" + postSeq +
                ", recArea='" + recArea + '\'' +
                ", recCapa=" + recCapa +
                ", finishYn='" + finishYn + '\'' +
                '}';
    }
}
