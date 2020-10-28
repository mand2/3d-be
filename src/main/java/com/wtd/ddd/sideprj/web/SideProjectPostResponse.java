package com.wtd.ddd.sideprj.web;

import com.wtd.ddd.sideprj.domain.SideProjectPost;
import com.wtd.ddd.sideprj.domain.SideProjectRecArea;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class SideProjectPostResponse {

    private int seq;
    private String leader;
    private String meeting;
    private String location;
    private String status;

    private int memCapa;
    private int memTotalCapa;

    private String title;
    private String contents;

    private Date createDt;

    private Map<String, Integer> recrutingArea;

    public static SideProjectPostResponse convert(SideProjectPost post, List<SideProjectRecArea> areas){
        Map<String, Integer> areaMap = new HashMap<>();
        for (SideProjectRecArea area : areas) {
            areaMap.put(area.getArea(), area.getMaxCapa() - area.getFixedCapa()); // response로 보내는 카운트는 현재 available한 인원수
        }
        return SideProjectPostResponse.builder().seq(post.getSeq())
                .leader(post.getLeader())
                .meeting(post.getMeeting())
                .location(post.getLocation())
                .status(post.getStatus())
                .memCapa(post.getMemCapa())
                .memTotalCapa(post.getMemTotalCapa())
                .title(post.getTitle())
                .contents(post.getContents())
                .createDt(post.getCreateDt())
                .recrutingArea(areaMap)
                .build();
    }


}
