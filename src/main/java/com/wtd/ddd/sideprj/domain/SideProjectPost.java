package com.wtd.ddd.sideprj.domain;

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

    private int memCapa;
    private int memTotalCapa;
    private String memInfoSeq;

    private String title;
    private String contents;

    private String deleteYn;
    private Date createDt;

}
