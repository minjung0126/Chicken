package com.chicken.project.reItem.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.member.model.dto.AdminImpl;
import com.chicken.project.member.model.dto.StoreImpl;
import com.chicken.project.reItem.model.dto.ReItemDTO;
import com.chicken.project.reItem.model.dto.ReListDTO;
import com.chicken.project.reItem.model.dto.StoreItemDTO;
import com.chicken.project.reItem.model.service.ReItemServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reItem/*")
public class ReItemController {

    private final ReItemServiceImpl reItemService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReItemController(ReItemServiceImpl reItemService) {
        this.reItemService = reItemService;
    }

    // 가맹점 반품서작성
    @GetMapping("/user/insertReItem")
    public ModelAndView reItem(ModelAndView mv, HttpServletRequest request, @AuthenticationPrincipal StoreImpl storeImpl){

        List<StoreItemDTO> storeItem = reItemService.selectItem(storeImpl.getStoreName());

        mv.addObject("storeItems", storeItem);
        mv.setViewName("reItem/user/insertReItem");
        return mv;
    }
    // 가맹점 반품서작성
    @PostMapping("/user/insertReItem")
    public ModelAndView reItems( String[] returnCount2
                                , String[] itemNo2
                                , @AuthenticationPrincipal StoreImpl storeImpl
                                , @ModelAttribute ReItemDTO returnItems
                                , ModelAndView mv
                                , @RequestParam String rReason
                                , @RequestParam int returnTotalMoney){

        List<ReItemDTO> insertItem = new ArrayList<>();


        for(int i = 0; i < returnCount2.length; i++){

            ReItemDTO reI = new ReItemDTO();
            reI.setItemNo(Integer.parseInt(itemNo2[i]));
            reI.setReturnTotalMoney(returnTotalMoney);
            reI.setrReason(rReason);

            if(returnCount2[i] != "") {
                reI.setReturnCount(Integer.parseInt(returnCount2[i]));
                insertItem.add(reI);
            }
        }

        int result = reItemService.insertReItem(insertItem, storeImpl.getStoreName());

        if(result > 0) {
            mv.setViewName("reItem/user/insertReItem");
        }

        return mv;
    }


    // 가맹점 반품서 리스트 확인
    @GetMapping("/user/storeReList")
    public ModelAndView returnList(ModelAndView mv
            , HttpServletRequest request
            , @RequestParam(defaultValue = "1") int currentPage
            , @AuthenticationPrincipal StoreImpl storeImpl
    ){

        int pageNo = currentPage;

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");
        String storeName = storeImpl.getStoreName();

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("storeName", storeName);

        int totalCount = reItemService.selectTotalCountS(searchMap);

        int limit = 6;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue, storeName);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, storeName);
        }

        log.info("tq" + selectCriteria);
        List<ReListDTO> storeReList = reItemService.selectReList(selectCriteria);

        mv.addObject("reList",storeReList);
        mv.addObject("selectCriteria", selectCriteria);
        mv.setViewName("reItem/user/storeReList");

        return mv;
    }

    // 가맹점 반품서 상세보기
    @GetMapping("/user/itemViewReList")
    public ModelAndView viewItem(ModelAndView mv
                                 , @AuthenticationPrincipal StoreImpl storeImpl
                                 , HttpServletRequest request
                                 , @ModelAttribute ReItemDTO returnItems
                                ){

        String rNo = request.getParameter("rNo");
        mv.addObject("rNo", rNo);

        Map<String, String> viewItem = new HashMap<>();
        viewItem.put("storeName", storeImpl.getStoreName());
        viewItem.put("rNo", rNo);

        ReItemDTO viewReItem = reItemService.viewReItem(rNo);
        List<ReItemDTO> viewReItems = reItemService.viewReItems(viewItem);

        mv.addObject("viewReItem", viewReItem);
        mv.addObject("viewReItems", viewReItems);

        return mv;

    }

    // 가맹점 반품서 수정
    @GetMapping("/user/reviseReItem")
    public ModelAndView RreItem(@ModelAttribute ReItemDTO returnItems
                                , ModelAndView mv
                                , HttpServletRequest request
                                , @AuthenticationPrincipal StoreImpl storeImpl
                                ){
        String rNo = request.getParameter("rNo");
        mv.addObject("rNo", rNo);

        log.info("혹시나 혹시나 혹시 나나나" + rNo);
        Map<String, String> item = new HashMap<>();
        item.put("storeName",storeImpl.getStoreName());
        item.put("rNo",rNo);
        item.put("returnItemNo", String.valueOf(returnItems.getReturnItemNo()));

        ReItemDTO updateItem = reItemService.selectUpReItem(rNo);
        List<ReItemDTO> updateItems = reItemService.selectReItems(item);
        List<StoreItemDTO> storeItems = reItemService.selectItems(item);

        mv.addObject("updateItem", updateItem);
        mv.addObject("updateItems",updateItems);
        mv.addObject("storeItems", storeItems);
        mv.addObject("rNo",rNo);

        return mv;
    }

    // 상품 추가
    @PostMapping("/user/insertOneReItem")
    @ResponseBody
    public String insertOne(@RequestParam int insertOne
                                , @AuthenticationPrincipal StoreImpl storeImpl
                                , @ModelAttribute ReItemDTO returnItems
                                , @RequestParam int rNo){
        String result = "failed";
        Map<String, Object> insertItem = new HashMap<>();
        insertItem.put("storeName",storeImpl.getStoreName());
        insertItem.put("rNo",rNo);
        insertItem.put("itemNo",insertOne);

        int insertOneItem = reItemService.insertOneItem(insertItem);

        if(insertOneItem > 0){
            result = "success";
        }
        return result;
    }

    // 상품 지우기
    @PostMapping("/user/deleteReItem")
    @ResponseBody
    public String deleteOne(@RequestParam int deleteNum
                            , @AuthenticationPrincipal StoreImpl storeImpl
                            , @ModelAttribute ReItemDTO returnItems
                            , @RequestParam int rNo
                            , @RequestParam int returnCount){

        String result = "failed";
        Map<String, Object> deleteItem = new HashMap<>();
        deleteItem.put("storeName",storeImpl.getStoreName());
        deleteItem.put("rNo",rNo);
        deleteItem.put("itemNo",deleteNum);
        deleteItem.put("returnCount", returnCount);

        int deleteOneItem = reItemService.deleteOneItem(deleteItem);


        if(deleteOneItem > 0){
            result = "success";
        }
        return result;
    }

    // 가맹점 수정완료
    @PostMapping("/user/reviseReItem")
    public ModelAndView updateReList(String[] returnCount2
                                    , String[] itemNo2
                                    , @AuthenticationPrincipal StoreImpl storeImpl
                                    , @ModelAttribute ReItemDTO returnItems
                                    , ModelAndView mv
                                    , @RequestParam String rReason
                                    , @RequestParam int total
                                    ,  String[] firstCount
                                    , @RequestParam int rNo
                                    , int[] returnItemNo){

        List<ReItemDTO> updateItem = new ArrayList<>();

        for(int i = 0; i < firstCount.length; i++){

            ReItemDTO reI = new ReItemDTO();
            reI.setItemNo(Integer.parseInt(itemNo2[i]));
            reI.setReturnTotalMoney(total);
            reI.setrReason(rReason);
            reI.setrNo(rNo);
            reI.setReturnItemNo(returnItemNo[i]);

            if(firstCount[i] != "") {
                reI.setReturnCount(Integer.parseInt(returnCount2[i]));
                reI.setFirstCount(Integer.parseInt(firstCount[i]));
                reI.setReturnItemNo(returnItemNo[i]);
                updateItem.add(reI);
            }

        }

        log.info("현석님 열심히 하세요 " + updateItem);
        int result = reItemService.updateReItem(updateItem, storeImpl.getStoreName());

        if(result > 0) {
            ReItemDTO update = reItemService.selectUpReItem(String.valueOf(rNo));
            mv.addObject("updateItem", update);
            mv.addObject("rNo",rNo);
            mv.setViewName("reItem/user/reviseReItem");
        }

        return mv;
    }

    // 가맹점 반품신청서 삭제
    @PostMapping("/user/storeReList")
    public ModelAndView DeleteReItem(ModelAndView mv, HttpServletRequest request){

        String rNo = request.getParameter("rNo");
        mv.addObject("rNo", rNo);

        int result = reItemService.deleteList(rNo);

        if (result > 0){
            mv.setViewName("reItem/user/storeReList");
        }

        return mv;
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
        mv.setViewName("reItem/admin/adminReList");

        return mv;

    }
    // 본사 제품 재등록
    @PostMapping("/admin/adminReList")
    public ModelAndView ReListUpdate(ModelAndView mv, HttpServletRequest request){

        String rNo = request.getParameter("rNo");
        mv.addObject("rNo",rNo);

        List<ReItemDTO> check = reItemService.selectList(rNo);

        Map<String, Object> maps = new HashMap<>();
        maps.put("rNo", rNo);
        maps.put("check", check);

        int result = reItemService.updateItem(maps);

        if (result > 0) {
            mv.setViewName("reItem/admin/adminReList");
        }

        return mv;
    }

    // 본사 반품 상세보기
    @GetMapping("/admin/adminReItem")
    public ModelAndView ReAcceptance(ModelAndView mv, HttpServletRequest request, RedirectAttributes rttr){

        String rNo = request.getParameter("rNo");
        String storeName = request.getParameter("storeName");
        String returnProgress = request.getParameter("returnProgress");
        mv.addObject("rNo", rNo);
        mv.addObject("storeName",storeName);
        mv.addObject("returnProgress", returnProgress);


        ReItemDTO reItem = reItemService.selectReturnItem(rNo);
        List<ReItemDTO> reItems = reItemService.selectReturnItems(rNo);

        mv.addObject("reItem",reItem);
        mv.addObject("reItems", reItems);

        rttr.addFlashAttribute("returnProgress", returnProgress);
        mv.setViewName("reItem/admin/adminReItem");

        return mv;
    }
    // 본사 반품 상세보기 반품등록 해주기
    @PostMapping("/admin/adminReItem")
    public ModelAndView ReturnComplete(ModelAndView mv
            , @AuthenticationPrincipal AdminImpl adminImpl
            , @ModelAttribute ReItemDTO returnItems
            , @RequestParam String rNo
            , @RequestParam String storeName){

        String adminId = adminImpl.getEmpId();

        int result = reItemService.updateComplete(returnItems, adminId, rNo, storeName);

        mv.setViewName("reItem/admin/adminReList");

        return mv;
    }


}
