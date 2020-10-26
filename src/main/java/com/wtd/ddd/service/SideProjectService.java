package com.wtd.ddd.service;

import com.wtd.ddd.controller.SideProjectPostRequest;
import com.wtd.ddd.controller.SideProjectPostResponse;
import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.domain.SideProjectRecArea;
import com.wtd.ddd.repository.SideProjectPostDAO;
import com.wtd.ddd.repository.SideProjectRecAreaDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SideProjectService {

    @Autowired
    SideProjectRecAreaDAO sideProjectRecAreaDAO;

    @Autowired
    SideProjectPostDAO sideProjectPostDAO;


    public String writePost(SideProjectPostRequest request) {
        // TODO : 인원수 validation, 코드 정리
        log.error("INPUT:" + request.toString());
        SideProjectPost post = SideProjectPostRequest.convertToPost(request);
        int key = sideProjectPostDAO.insert(post);
        List<SideProjectRecArea> areas = SideProjectPostRequest.convertToRecArea(request, key);
        addRecAreas(areas);
        return "SUCCESS";
    }

    public SideProjectPostResponse get(int seq) {
        SideProjectPost post = sideProjectPostDAO.select(seq).get(0);
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.selectByPostSeq(seq);
        return SideProjectPostResponse.convert(post, areas);
    }



    private boolean addRecAreas(List<SideProjectRecArea> areas) {
        for (SideProjectRecArea recArea : areas) {
            sideProjectRecAreaDAO.insert(recArea);
        }
        return true;
    }


}
