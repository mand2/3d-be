package com.wtd.ddd.controller;

import lombok.Data;

@Data
public class SideProjectApplyRequest {

    private int postSeq;
    private String memId;
    private String recArea;
    private String message;

}
