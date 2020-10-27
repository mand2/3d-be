package com.wtd.ddd.domain;

import com.wtd.ddd.controller.SideProjectApplyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SideProjectApply {
    private int seq;
    private int postSeq;
    private String memId;
    private String recArea;
    private String message;
    private String applyStat;
    private Date createDt;
    private Date updateDt;

    public static SideProjectApply convert(SideProjectApplyRequest request) {
        return SideProjectApply.builder()
                .postSeq(request.getPostSeq())
                .memId(request.getMemId())
                .recArea(request.getRecArea())
                .message(request.getMessage())
                .build();
    }
}
