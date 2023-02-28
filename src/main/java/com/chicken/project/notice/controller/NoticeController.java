package com.chicken.project.notice.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.notice.NoticeDeleteException;
import com.chicken.project.notice.model.dto.NoticeDTO;
import com.chicken.project.notice.model.dto.NoticeFileDTO;
import com.chicken.project.notice.model.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {

    private final NoticeService noticeService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public NoticeController(NoticeService noticeService){

        this.noticeService = noticeService;
    }

    /* 관리자 공지사항 조회 */
    @GetMapping("/admin/list")
    public ModelAndView adminNoticeList(ModelAndView mv, HttpServletRequest request){

        String currentPage = request.getParameter("currentPage");
        int pageNo = 1;

        if(currentPage != null && !"".equals(currentPage)) {
            pageNo = Integer.parseInt(currentPage);
        }

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        log.info("[NoticeController] searchMap = " + searchMap);

        int totalCount = noticeService.selectTotalCount(searchMap);

        log.info("[NoticeController] totalCount = " + totalCount);

        int limit = 6;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        log.info("[NoticeController] selectCriteria = " + selectCriteria);

        List<NoticeDTO> noticeList = noticeService.selectNoticeList(selectCriteria);

        log.info("[NoticeController] noticeList = " + noticeList);

        mv.addObject("noticeList", noticeList);
        mv.addObject("selectCriteria", selectCriteria);
        mv.setViewName("notice/admin/adminNoticeList");

        return mv;
    }

    /* 유저 공지사항 조회 */
    @GetMapping("/user/list")
    public ModelAndView userNoticeList(ModelAndView mv, HttpServletRequest request){

        String currentPage = request.getParameter("currentPage");
        int pageNo = 1;

        if(currentPage != null && !"".equals(currentPage)) {
            pageNo = Integer.parseInt(currentPage);
        }

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        log.info("[NoticeController] searchMap = " + searchMap);

        int totalCount = noticeService.selectTotalCount(searchMap);

        log.info("[NoticeController] totalCount = " + totalCount);

        int limit = 6;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        log.info("[NoticeController] selectCriteria = " + selectCriteria);

        List<NoticeDTO> noticeList = noticeService.selectNoticeList(selectCriteria);

        log.info("[NoticeController] noticeList = " + noticeList);

        mv.addObject("noticeList", noticeList);
        mv.addObject("selectCriteria", selectCriteria);
        mv.setViewName("notice/user/userNoticeList");

        return mv;
    }

    /* 관리자 공지사항 등록 페이지 이동 */
    @GetMapping("/admin/insert")
    public String noticeInsertPage(){

        return "notice/admin/adminNoticeInsert";
    }

    /* 관리자 공지사항 등록 */
    @PostMapping("/admin/insert")
    public String noticeInsert(@ModelAttribute NoticeDTO notice,
                               @RequestParam(value="file", required=false) MultipartFile file,
                               RedirectAttributes rttr
                               ) throws Exception{

        NoticeFileDTO noticeFile = new NoticeFileDTO();

        int result = noticeService.noticeInsert(notice);

        log.info("[NoticeController]" + notice);
        log.info("[NoticeController]" + file);

        String root = ResourceUtils.getURL("src/main/resources").getPath();

        String filePath = root + "static/uploadFiles";

        log.info("루트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + filePath);

        File mkdir = new File(filePath);
        if(!mkdir.exists()) {
            mkdir.mkdirs();
        }

        String originFileName = "";
        String ext = "";
        String changeName = "";
        String savedPath = "";

        if(file.getSize() > 0) {
            originFileName = file.getOriginalFilename();
            ext = originFileName.substring(originFileName.lastIndexOf("."));
            changeName = UUID.randomUUID().toString().replace("-",  "");
            savedPath = filePath + "/" + changeName + ext;

            noticeFile.setOriginName(originFileName);
            noticeFile.setFileName(changeName + ext);
            noticeFile.setSavedPath(savedPath);

            noticeService.noticeFileInsert(noticeFile);

            try {
                file.transferTo(new File(filePath + "\\" + changeName + ext));
            } catch (IOException e) {

                e.printStackTrace();
                new File(filePath + "\\" + changeName + ext).delete();
            }
        }

        rttr.addFlashAttribute("message", "공지사항 등록 성공!");

        return "notice/admin/list";
    }

    /* 관리자 공지사항 상세페이지 */
    @GetMapping("/admin/detail")
    public ModelAndView adminNoticeDetail(ModelAndView mv, HttpServletRequest request){

        int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

        System.out.println("noticeNo = " + noticeNo);

        NoticeDTO noticeDetail = noticeService.noticeDetailByNo(noticeNo);

        System.out.println(noticeDetail);

        mv.addObject("noticeDetail", noticeDetail);
        mv.setViewName("notice/admin/adminNoticeDetail");

        return mv;
    }

    /* 유저 공지사항 상세페이지 */
    @GetMapping("/user/detail")
    public ModelAndView userNoticeDetail(ModelAndView mv, HttpServletRequest request){

        int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

        System.out.println("noticeNo = " + noticeNo);

        NoticeDTO noticeDetail = noticeService.noticeDetailByNo(noticeNo);

        System.out.println(noticeDetail);

        mv.addObject("noticeDetail", noticeDetail);
        mv.setViewName("notice/user/userNoticeDetail");

        return mv;
    }

    /* 관리자 공지사항 삭제 */
    @GetMapping("/admin/delete")
    public String deleteNotice(HttpServletRequest request, RedirectAttributes rttr) throws NoticeDeleteException {

        int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

        int result = noticeService.deleteNotice(noticeNo);

        rttr.addFlashAttribute("message", "공지사항 삭제 성공!");

        return "notice/admin/list";
    }

    /* 관리자 공지사항 수정 페이지 이동 */
    @GetMapping("/admin/update")
    public ModelAndView updateNoticePage(HttpServletRequest request, ModelAndView mv) {

        int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

        NoticeDTO noticeDetail = noticeService.noticeDetailByNo(noticeNo);

        mv.addObject("noticeDetail", noticeDetail);
        mv.setViewName("notice/admin/adminNoticeUpdate");

        return mv;
    }

    /* 관리자 공지사항 수정 */
    @PostMapping("/admin/update")
    public String updateNotice(@ModelAttribute NoticeDTO notice,
                               @RequestParam int noticeNo,
                               @RequestParam(value="file", required=false) MultipartFile file,
                               RedirectAttributes rttr) throws Exception{

        log.info("[NoticeController]" + notice);
        log.info("[NoticeController]" + file);

        String root = ResourceUtils.getURL("src/main/resources").getPath();

        String filePath = root + "static/uploadFiles";

        log.info("루트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ" + filePath);

        File mkdir = new File(filePath);
        if(!mkdir.exists()) {
            mkdir.mkdirs();
        }

        String originFileName = "";
        String ext = "";
        String changeName = "";
        String savedPath = "";

        int result = noticeService.updateNotice(notice);

        if(file.getSize() > 0) {
            originFileName = file.getOriginalFilename();
            ext = originFileName.substring(originFileName.lastIndexOf("."));
            changeName = UUID.randomUUID().toString().replace("-",  "");
            savedPath = filePath + "/" + changeName + ext;

            NoticeFileDTO noticeFile = new NoticeFileDTO();

            if(result > 0) {

                if(notice.getNoticeFile() != null){

                    int result2 = noticeService.deleteNoticeFile(noticeNo);

                    if(result2 > 0) {

                        noticeFile.setNoticeNo(noticeNo);
                        noticeFile.setOriginName(originFileName);
                        noticeFile.setFileName(changeName);
                        noticeFile.setSavedPath(savedPath);

                        noticeService.updateNoticeFile(noticeFile);
                    }

                } else {

                        noticeFile.setNoticeNo(noticeNo);
                        noticeFile.setOriginName(originFileName);
                        noticeFile.setFileName(changeName);
                        noticeFile.setSavedPath(savedPath);

                        noticeService.updateNoticeFile(noticeFile);
                    }

            }

            try {
                file.transferTo(new File(filePath + "\\" + changeName + ext));
            } catch (IOException e) {

                e.printStackTrace();
                new File(filePath + "\\" + changeName + ext).delete();
            }
        }

        rttr.addFlashAttribute("message", "공지사항 수정 성공!");

        return "notice/admin/list";

        }
}
