package com.wtd.ddd.service;

import com.wtd.ddd.domain.SideProjectRecArea;
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

    public boolean addRecAreas(List<SideProjectRecArea> areas) {

        for (SideProjectRecArea recArea : areas) {
            sideProjectRecAreaDAO.insert(recArea);
        }

        return true;
    }

}
