package com.wtd.ddd.util;

import com.wtd.ddd.web.SideProjectPostRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
public class SideProjectValidator {
    public static boolean isValidCapacity(SideProjectPostRequest request) {
        int areaCapacity = 0;
        int totalCapacity = request.getMemTotalCapa();
        Map<String, Integer> areas = request.getRecruitingArea();
        Set<String> keySet = areas.keySet();
        for (String key : keySet) {
            if (areas.get(key) > 0) areaCapacity += areas.get(key);
        }
        return areaCapacity == totalCapacity;
    }
}
