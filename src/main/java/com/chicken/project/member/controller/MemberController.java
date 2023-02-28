package com.chicken.project.member.controller;

import com.chicken.project.member.model.dto.EmployeeDTO;
import com.chicken.project.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;


@Controller
@RequestMapping("/member/*")
public class MemberController {


    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping("/login")
    public void login(){}

    @GetMapping("/main/admin_main")
    public void empMain(){}

    @GetMapping("/failure")
    public String loginFail(){

        return "member/failure";
    };


}
