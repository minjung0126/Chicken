package com.chicken.project.counsel.controller;

import com.chicken.project.counsel.model.dto.CounselApplyDTO;
import com.chicken.project.counsel.model.dto.CounselDTO;
import com.chicken.project.counsel.model.service.CounselService;
import com.chicken.project.member.model.dto.StoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/counsel/*")
public class CounselController {

    private final CounselService counselService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CounselController(CounselService counselService){

        this.counselService = counselService;
    }

    /* 유저 내가 쓴 1:1 문의 조회 */
    @GetMapping("/user/list")
    public ModelAndView userCounselListPage(ModelAndView mv, @AuthenticationPrincipal User user){

        String storeName = ((StoreImpl) user).getStoreName();

        log.info("[CounselController] storeName : " + storeName);

        List<CounselDTO> counselList = counselService.selectCounselByStoreName(storeName);

        mv.addObject("counselList", counselList);
        mv.setViewName("counsel/user/userCounselList");

        return mv;
    }

    /* 관리자 1:1 문의 조회 */
    @GetMapping("/admin/list")
    public ModelAndView adminCounselListPage(ModelAndView mv){

        List<CounselDTO> counselList = counselService.selectCounsel();

        mv.addObject("counselList", counselList);
        mv.setViewName("counsel/admin/adminCounselList");

        return mv;
    }

    /* 유저 1:1 문의 등록 페이지 이동 */
    @GetMapping("/user/insert")
    public String counselInsertPage(){

        return "counsel/user/userCounselInsert";
    }

    /* 유저 1:1 문의 등록 */
    @PostMapping("/user/insert")
    public String userCounselInsert(@ModelAttribute CounselDTO counsel, RedirectAttributes rttr, ModelAndView mv){

        log.info("[CounselController] counsel : " + counsel);

        counselService.insertCounsel(counsel);

        mv.addObject("counsel", counsel);
        rttr.addFlashAttribute("message", "1:1 문의 등록 성공!");

        return "redirect:/counsel/user/list";
    }

    /* 관리자 1:1 문의 등록 상세페이지 */
    @GetMapping("/admin/detail")
    public ModelAndView adminCounselDetail(ModelAndView mv, @RequestParam int counselNo){

        log.info("[CounselController] counselNo : " + counselNo);

        CounselDTO counsel = counselService.selectCounselByNo(counselNo);
        CounselApplyDTO counselApply = counselService.selectCounselApplyByNo(counselNo);

        mv.addObject("counsel", counsel);
        mv.addObject("counselApply", counselApply);
        mv.setViewName("counsel/admin/adminCounselDetail");

        return mv;
    }

    /* 관리자 1:1 문의 답변 등록 페이지 이동 */
    @GetMapping("/admin/insert")
    public ModelAndView adminCounselInsertPage(ModelAndView mv, HttpServletRequest request){

        int counselNo = Integer.parseInt(request.getParameter("counselNo"));

        log.info("[CounselController] counselNo : " + counselNo);

        CounselDTO counsel = counselService.selectCounselByNo(counselNo);

        log.info("[CounselController] counsel : " + counsel);

        mv.addObject("counsel", counsel);
        mv.setViewName("counsel/admin/adminCounselInsert");

        return mv;
    }

    /* 관리자 1:1 문의 답변 등록 */
    @PostMapping("/admin/insert")
    public String adminCounselInsert(@RequestParam String answerContent,
                                     HttpServletRequest request,
                                     ModelAndView mv,
                                     RedirectAttributes rttr){

        int counselNo = Integer.parseInt(request.getParameter("counselNo"));
        String empName = request.getParameter("empName");

        log.info("[CounselController] counselNo : " + counselNo);

        counselService.insertCounselApply(answerContent, counselNo, empName);

        rttr.addFlashAttribute("message", "1:1문의 답변 등록 완료!");

        return "redirect:/counsel/admin/list";
    }

    /* 유저 1:1 문의 상세페이지 */
    @GetMapping("/user/detail")
    public ModelAndView userCounselPage(ModelAndView mv, @RequestParam int counselNo){

        log.info("[CounselController] couneslNo : " + counselNo);

        CounselDTO counsel = counselService.selectCounselByNo(counselNo);
        CounselApplyDTO counselApply = counselService.selectCounselApplyByNo(counselNo);

        mv.addObject("counsel", counsel);
        mv.addObject("counselApply", counselApply);

        mv.setViewName("counsel/user/userCounselDetail");

        return mv;

    }
}
