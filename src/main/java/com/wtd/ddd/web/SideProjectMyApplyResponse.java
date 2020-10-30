package com.wtd.ddd.web;

import lombok.Data;

import java.util.Date;

@Data
public class SideProjectMyApplyResponse {
    private int applySeq;
    private String myStatus;
    private String prjStatus;
    private int memCapa;
    private int memTotalCapa;
    private String title;
    private Date createDt;
}
