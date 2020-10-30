package com.wtd.ddd.web;

import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.domain.SideProjectRecArea;
import lombok.Data;

import java.util.*;

@Data
public class SideProjectPostRequest {

    private String leader;
    private String meeting;
    private String location;
    private int memTotalCapa;

    private String title;
    private String contents;

    private Map<String, Integer> recruitingArea;

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
        Map<String, Integer> areas = filterRecruitingAreaCapaMap(request);
        Set<String> keySet = areas.keySet();
        for (String key : keySet) {
            result.add(
                    SideProjectRecArea.builder()
                            .postSeq(id)
                            .area(key)
                            .maxCapa(areas.get(key))
                            .build()
            );
        }
        return result;
    }

    private static Map<String, Integer> filterRecruitingAreaCapaMap(SideProjectPostRequest request) {
        Map<String, Integer> recruitingAreas = request.getRecruitingArea();
        Map<String, Integer> filteredRecruitingAreas = new HashMap<>();
        Set<String> keySet = recruitingAreas.keySet();
        for (String key : keySet) {
            if (recruitingAreas.get(key) > 0) {
                filteredRecruitingAreas.put(key, recruitingAreas.get(key));
            }
        }
        return filteredRecruitingAreas;


    }



}
