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
    private String area;
    private int fixedCapa;
    private int maxCapa;
    private String finishYn;

}
