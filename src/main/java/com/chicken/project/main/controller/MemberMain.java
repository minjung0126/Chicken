package com.chicken.project.main.controller;

import com.chicken.project.member.model.dto.AdminImpl;
import com.chicken.project.member.model.dto.StoreImpl;
import com.chicken.project.notice.model.dto.NoticeDTO;
import com.chicken.project.notice.model.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/member/*")
public class MemberMain {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final NoticeService noticeService;

    @Autowired
    public MemberMain(NoticeService noticeService){

        this.noticeService = noticeService;
    }

    @RequestMapping("/moveMain")
    public ModelAndView defaultLocation(@AuthenticationPrincipal User user, ModelAndView mv){
        System.out.println("확인");
        String url = "/member/login";

        log.info("[MemberImpl]================================= ");
        log.info("[MemberImpl] user = " + user);

        if(user instanceof AdminImpl) {

            if(((AdminImpl)user).getEmpRoleList().get(0).getAuthCode().equals("1") || ((AdminImpl)user).getEmpRoleList().get(0).getAuthCode().equals("2")){

                List<NoticeDTO> noticeList = noticeService.selectMainNotice();

                mv.addObject("noticeList", noticeList);
                mv.setViewName("/main/admin_main");
            }
        }

        if(user instanceof StoreImpl){

            if(((StoreImpl)user).getStoreRoleList().get(0).getAuthCode().equals("3")){

                System.out.println("확인용 = " + url);

                List<NoticeDTO> noticeList = noticeService.selectMainNotice();

                mv.addObject("noticeList", noticeList);
                mv.setViewName("/main/user_main");
            }
        }
//        System.out.println("((MemberImpl)user = " + ((MemberImpl)user).getEmpRoleList().get(0).getAuthCode());

        return mv;
    }
}
