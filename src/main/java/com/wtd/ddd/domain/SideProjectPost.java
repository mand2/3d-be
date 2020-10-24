package com.wtd.ddd.domain;

import lombok.Data;

@Data
public class SideProjectPost {

    String title;
    String contents;

    @Override
    public String toString() {
        return "SideProjectPost{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

}
