package com.wtd.ddd.service;

import com.wtd.ddd.domain.Mail;
import com.wtd.ddd.domain.SideProjectApply;
import com.wtd.ddd.repository.sideprj.SideProjectApplyDAO;
import com.wtd.ddd.util.SideProjectValidator;
import com.wtd.ddd.web.SideProjectApplyRequest;
import com.wtd.ddd.web.SideProjectPostRequest;
import com.wtd.ddd.web.SideProjectPostResponse;
import com.wtd.ddd.domain.SideProjectPost;
import com.wtd.ddd.domain.SideProjectRecArea;
import com.wtd.ddd.repository.sideprj.SideProjectPostDAO;
import com.wtd.ddd.repository.sideprj.SideProjectRecAreaDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SideProjectService {

    private final SideProjectRecAreaDAO sideProjectRecAreaDAO;
    private final SideProjectPostDAO sideProjectPostDAO;
    private final SideProjectApplyDAO sideProjectApplyDAO;
    private final MailService mailService;

    public SideProjectService(SideProjectRecAreaDAO sideProjectRecAreaDAO,
                              SideProjectPostDAO sideProjectPostDAO,
                              SideProjectApplyDAO sideProjectApplyDAO,
                              MailService mailService) {
        this.sideProjectRecAreaDAO = sideProjectRecAreaDAO;
        this.sideProjectPostDAO = sideProjectPostDAO;
        this.sideProjectApplyDAO = sideProjectApplyDAO;
        this.mailService = mailService;
    }


    public String writePost(SideProjectPostRequest request) {
        SideProjectPost post = SideProjectPostRequest.convertToPost(request);
        if (!SideProjectValidator.isValidCapacity(request)) return "인원수가 맞지 않습니다!";
        int key = sideProjectPostDAO.insert(post);
        List<SideProjectRecArea> areas = SideProjectPostRequest.convertToRecArea(request, key);
        addRecAreas(areas);
        mailService.sendMail("mirijo02233092@gmail.com ","[3D] 프로젝트가 새로 등록되었습니다!",
                Mail.convertToMailContent(post)); // 테스트를 위한 하드코딩
        return "등록 성공!";
    }

    public SideProjectPostResponse get(int seq) {
        SideProjectPost post = sideProjectPostDAO.select(seq).get(0);
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.selectByPostSeq(seq);
        return SideProjectPostResponse.convert(post, areas);
    }

    @Transactional
    public boolean changeApplyStatus(SideProjectApply apply) {
        int seq = apply.getSeq();
        int recSeq = apply.getRecAreaSeq();
        sideProjectApplyDAO.update(apply);
        if ("Accept".equals(apply.getApplyStat())) {
            sideProjectRecAreaDAO.updateCapacity(recSeq);
            sideProjectPostDAO.updateCapacity(seq);

            if (reachedMaxCapaOfRecArea(recSeq)) {
                sideProjectRecAreaDAO.updateToFinish(seq);
            }
            if (reachedMaxCapaOfProject(seq)) {
                sideProjectPostDAO.updateToFinish(seq);
            }
        }
        return true;
    }

    public int addApply(SideProjectApplyRequest request) {
        int recAreaSeq = sideProjectRecAreaDAO.findRecAreaSeq(request.getPostSeq(), request.getRecArea());
        SideProjectApply apply = SideProjectApply.convert(request, recAreaSeq);
        return sideProjectApplyDAO.insert(apply);
    }

    private boolean reachedMaxCapaOfRecArea(int seq) {
        List<SideProjectRecArea> areas = sideProjectRecAreaDAO.selectBySeq(seq);
        if (areas.size() != 1) return false;
        return areas.get(0).getMaxCapa() == areas.get(0).getFixedCapa();
    }

    private boolean reachedMaxCapaOfProject(int seq) {
        List<SideProjectPost> posts = sideProjectPostDAO.selectByApplySeq(seq);
        if (posts.size() != 1) return false;
        return posts.get(0).getMemCapa() == posts.get(0).getMemTotalCapa();
    }


    private boolean addRecAreas(List<SideProjectRecArea> areas) {
        for (SideProjectRecArea recArea : areas) {
            sideProjectRecAreaDAO.insert(recArea);
        }
        return true;
    }


}
