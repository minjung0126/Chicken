package com.chicken.project.reItem.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.reItem.model.dto.ReItemDTO;
import com.chicken.project.reItem.model.dto.ReListDTO;
import com.chicken.project.reItem.model.service.ReItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reItem/*")
public class ReItemController {

    private final ReItemService reItemService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReItemController(ReItemService reItemService) {
        this.reItemService = reItemService;
    }

    // 가맹점 반품서작성
    @GetMapping("/user/insertReItem")
    public void reItem(){



    }

    // 가맹점 반품서 리스트
    @GetMapping("/user/storeReList")
    public ModelAndView returnList(ModelAndView mv, HttpServletRequest request, @RequestParam(defaultValue = "1") int currentPage){

        int pageNo = currentPage;

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        int totalCount = reItemService.selectTotalCount(searchMap);

        int limit = 6;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        List<ReListDTO> storeReList = reItemService.selectReList(selectCriteria);

        mv.addObject("reList",storeReList);
        mv.addObject("selectCriteria", selectCriteria);

        return mv;
    }

    // 가맹점 반품서 수정
    @GetMapping("/user/reviseReItem")
    public void RreItem(){

    }

    // 본사 반품서 리스트 보기
    @GetMapping("/admin/adminReList")
    public ModelAndView ReList(ModelAndView mv, HttpServletRequest request, @RequestParam(defaultValue = "1") int currentPage){

        int pageNo = currentPage;

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        int totalCount = reItemService.selectTotalCount(searchMap);

        int limit = 6;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        List<ReListDTO> storeReturnList = reItemService.selectReturnList(selectCriteria);

        mv.addObject("returnList",storeReturnList);
        mv.addObject("selectCriteria", selectCriteria);

        return mv;

    }
    @GetMapping("/admin/adminReItem")
    public ModelAndView ReAcceptance(ModelAndView mv, HttpServletRequest request, Model model){

        String rNo = request.getParameter("rNo");
        model.addAttribute("rNo",rNo);
        log.info("rNo 확인 " + rNo);

        ReItemDTO reItem = reItemService.selectReturnItem(rNo);
        List<ReItemDTO> reItems = reItemService.selectReturnItemS(rNo);

        mv.addObject("reItem",reItem);
        mv.addObject("reItems", reItems);
        mv.setViewName("/reItem/admin/adminReItem");

        return mv;
    }


}
