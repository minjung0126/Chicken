package com.chicken.project.mypage.controller;

import com.chicken.project.common.util.SessionUtil;
import com.chicken.project.member.model.dto.EmployeeDTO;
import com.chicken.project.member.model.dto.StoreMemberDTO;
import com.chicken.project.mypage.service.MypageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.swing.JOptionPane.showMessageDialog;

@Controller
@RequestMapping("/mypage/*")
public class MypageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MypageServiceImpl mypageService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MypageController(MypageServiceImpl mypageService, PasswordEncoder passwordEncoder) {
        this.mypageService = mypageService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/member_mypage")
    public String empMypage(){

        return "mypage/member_mypage";
    }

    @PostMapping("/member_mypage")
    public String updateEmpPwd(@AuthenticationPrincipal User user, @ModelAttribute EmployeeDTO emp, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr){

        emp.setEmpId(user.getUsername());
        emp.setEmpPwd(passwordEncoder.encode(emp.getEmpPwd()));
        log.info("[MypageController] emp : " + emp);

        int result = mypageService.updateEmpPwd(emp);

        SessionUtil.invalidateSession(request, response);

        if(result > 0){

            rttr.addFlashAttribute("message", "비밀번호가 수정되었습니다. 다시 로그인해주세요.");
        }

        return "redirect:/";
    }


    @GetMapping("/store_mypage")
    public void storeMypage(){

    }

    @PostMapping("/store_mypage")
    public String updateStorePwd(@AuthenticationPrincipal User user, @ModelAttribute StoreMemberDTO store, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr){

        store.setStoreId(user.getUsername());
        store.setStorePwd(passwordEncoder.encode(store.getStorePwd()));
        log.info("[MypageController] store : " + store);

        int result = mypageService.updateStorePwd(store);

        SessionUtil.invalidateSession(request, response);

        if(result > 0){

            rttr.addFlashAttribute("message", "비밀번호가 수정되었습니다. 다시 로그인해주세요.");
        }

        return "redirect:/";
    }
}
