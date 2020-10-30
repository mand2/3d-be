package com.wtd.ddd.web;

import lombok.Data;

@Data
public class SideProjectStatusChangeRequest {
    private int applySeq;
    private int recSeq;
    private String statusToChange;

}
