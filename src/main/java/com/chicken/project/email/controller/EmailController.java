package com.chicken.project.email.controller;

import com.chicken.project.common.util.SessionUtil;
import com.chicken.project.email.model.service.EmailService;
import com.chicken.project.member.model.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/member/*")
public class EmailController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public EmailController(EmailService emailService, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/ckEmail")
    public ModelAndView ckEmail(ModelAndView mv){

        mv.setViewName("member/ckEmail");
        return mv;
    };

    @PostMapping("/ckEmail")
    public ModelAndView emailConfirm(ModelAndView mv, EmployeeDTO emp, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        emp.setEmpId(request.getParameter("ckId"));
        emp.setEmpEmail(request.getParameter("ckEmail"));

        int result = emailService.updateEmailCode(emp);


        if(result > 0){

            mv.addObject("mvId", emp.getEmpId());
            mv.addObject("mvEmail", emp.getEmpEmail());

            log.info("empId : " + emp.getEmpId());
            log.info("empEmail : " + emp.getEmpEmail());

        }

        log.info( "result : " + result);
        mv.setViewName("member/updatePwd");
        return mv;
    }

    @PostMapping("/updatePwd")
    public String sendEmail(EmployeeDTO emp, HttpServletRequest request, HttpServletResponse response){

        emp.setEmpId(request.getParameter("empId"));
        emp.setEmpEmail(request.getParameter("email"));
        emp.setEmpPwd(passwordEncoder.encode(request.getParameter("new_pwd")));
        emp.setEmailCode(request.getParameter("code"));

        log.info("[EmailController] emp : " + emp);

        EmployeeDTO emailCode = emailService.selectEmailCode(emp);

        if(emailCode.getEmailCode().equals(emp.getEmailCode())){

            emp.setEmailCode(emailCode.getEmailCode());
            int result = emailService.updatePwd(emp);

            if(result > 0 ){
                SessionUtil.invalidateSession(request, response);
            }
        }

        return "redirect:/";
    }
}
