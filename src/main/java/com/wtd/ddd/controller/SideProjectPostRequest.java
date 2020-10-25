package com.wtd.ddd.controller;

import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.domain.SideProjectRecArea;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class SideProjectPostRequest {

    private String leader;
    private String meeting;
    private String location;
    private String status;
    private int memTotalCapa;

    private String title;
    private String contents;

    private Map<String, Integer> recrutingArea;

    public static SideProjectPost convertToPost(SideProjectPostRequest request) {
        return SideProjectPost.builder()
                .leader(request.getLeader())
                .meeting(request.getMeeting())
                .location(request.getLocation())
                .memTotalCapa(request.getMemTotalCapa())
                .title(request.getTitle())
                .contents(request.getContents())
                .build();
    }

    public static List<SideProjectRecArea> convertToRecArea(SideProjectPostRequest request, int id) {
        List<SideProjectRecArea> result = new ArrayList<>();
        Map<String, Integer> recrutingAreas = request.getRecrutingArea();
        Set<String> keySet = recrutingAreas.keySet();
        for (String key : keySet) {
            result.add(
                    SideProjectRecArea.builder()
                            .postSeq(id)
                            .recArea(key)
                            .recCapa(recrutingAreas.get(key))
                            .build()
            );
        }
        return result;
    }



}
