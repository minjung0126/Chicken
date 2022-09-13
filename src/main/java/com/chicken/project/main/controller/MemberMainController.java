package com.chicken.project.main.controller;

import com.chicken.project.member.model.dto.AdminImpl;
import com.chicken.project.member.model.dto.StoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member/*")
public class MemberMain {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/moveMain")
    public String defaultLocation(@AuthenticationPrincipal User user){
        System.out.println("확인");
        String url = "/member/login";

        log.info("[MemberImpl]================================= ");
        log.info("[MemberImpl] user = " + user);

        if(user instanceof AdminImpl) {

            if(((AdminImpl)user).getEmpRoleList().get(0).getAuthCode().equals("1") || ((AdminImpl)user).getEmpRoleList().get(0).getAuthCode().equals("2")){


                url = "/main/admin_main";

            }
        }

        if(user instanceof StoreImpl){

           if(((StoreImpl)user).getStoreRoleList().get(0).getAuthCode().equals("3")){
                System.out.println("확인용 = " + url);
                url = "/main/user_main";
            }
        }
//        System.out.println("((MemberImpl)user = " + ((MemberImpl)user).getEmpRoleList().get(0).getAuthCode());

        return url;
    }
}