package com.wtd.ddd.domain;

import lombok.Data;

@Data
public class SideProjectPost {

    private int seq;
    private String title;
    private String contents;

    @Override
    public String toString() {
        return "SideProjectPost{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

}
