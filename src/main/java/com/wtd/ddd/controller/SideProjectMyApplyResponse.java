package com.wtd.ddd.controller;

import lombok.Data;

import java.util.Date;

@Data
public class SideProjectMyApplyResponse {
    private int seq;
    private String status;
    private int memCapa;
    private int memTotalCapa;
    private String title;
    private Date createDt;
}
