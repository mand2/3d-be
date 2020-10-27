package com.wtd.ddd.controller;

import com.wtd.ddd.domain.SideProjectPost;
import lombok.Builder;

import java.util.List;

@Builder
public class SideProjectPostSearchResponse {
    int totalCount;
    List<SideProjectPost> posts;
}
