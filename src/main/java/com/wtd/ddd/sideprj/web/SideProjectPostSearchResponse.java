package com.wtd.ddd.sideprj.web;

import com.wtd.ddd.sideprj.domain.SideProjectPost;
import lombok.Builder;

import java.util.List;

@Builder
public class SideProjectPostSearchResponse {
    int totalCount;
    List<SideProjectPost> posts;
}
