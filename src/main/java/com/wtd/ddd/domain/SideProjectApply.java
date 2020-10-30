package com.wtd.ddd.domain;

import com.wtd.ddd.web.SideProjectApplyRequest;
import com.wtd.ddd.web.SideProjectStatusChangeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SideProjectApply {
    private int seq;
    private int recAreaSeq;
    private int postSeq;
    private String memId;
    private String recArea;
    private String message;
    private String applyStat;
    private Date createDt;
    private Date updateDt;

    private static final List<String> APPLY_STATUS = Arrays.asList("Accept", "Decline");

    public static SideProjectApply convert(SideProjectApplyRequest request, int recAreaSeq) {
        return SideProjectApply.builder()
                .postSeq(request.getPostSeq())
                .recAreaSeq(recAreaSeq)
                .memId(request.getMemId())
                .recArea(request.getRecArea())
                .message(request.getMessage())
                .build();
    }

    public static SideProjectApply convertToChangeStatus(SideProjectStatusChangeRequest request) {
        return SideProjectApply.builder()
                .seq(request.getApplySeq())
                .recAreaSeq(request.getRecSeq())
                .applyStat(request.getStatusToChange())
                .build();
    }

    public static boolean validate(SideProjectStatusChangeRequest request) {
        return APPLY_STATUS.contains(request.getStatusToChange());
    }
}
