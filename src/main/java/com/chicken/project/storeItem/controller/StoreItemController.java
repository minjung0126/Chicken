package com.chicken.project.storeItem.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.member.model.dto.StoreImpl;
import com.chicken.project.storeItem.model.dto.StoreItemListDTO;
import com.chicken.project.storeItem.model.service.StoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/storeItem")
public class StoreItemController {

    private final StoreItemService storeItemService;

    @Autowired
    public StoreItemController(StoreItemService storeItemService) {
        this.storeItemService = storeItemService;
    }

    @GetMapping("/itemListSelect")
    public ModelAndView itemListSelect(ModelAndView mv, HttpServletRequest request, @AuthenticationPrincipal StoreImpl storeImpl){

        String storeName = storeImpl.getStoreName();
        String currentPage = request.getParameter("currentPage");

        int pageNo = 1;

        if(currentPage != null && !"".equals(currentPage)){

            pageNo = Integer.parseInt(currentPage);
        }

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("storeName", storeName);

        int totalCount = storeItemService.selectTotalCount(searchMap);

        int limit = 8;

        int buttonAmount = 5;

        SelectCriteria selectCriteria = null;
        if(searchCondition != null && !"".equals(searchCondition)){

            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue, storeName);
        } else {

            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, storeName);
        }

        List<StoreItemListDTO> itemList = storeItemService.itemListSelect(selectCriteria);

        System.out.println("itemList: " + itemList);
        System.out.println("selectCriteria : " + selectCriteria);

        mv.addObject("selectCriteria", selectCriteria);
        mv.addObject("itemList", itemList);
        mv.setViewName("storeItem/storeItem_list");
        return mv;
    }

    @PostMapping("/storeItemUpdate")
    @ResponseBody
    public Object storeItemUpdate(@RequestParam("itemNo") String itemNo,
                                @RequestParam("storeAmount") String storeAmount,
                                @AuthenticationPrincipal StoreImpl storeImple) {

        String storeName = storeImple.getStoreName();

        System.out.println("storeName:" + storeName);
        System.out.println("itemNo:" + itemNo);
        System.out.println("storeAmount:" + storeAmount);

        int result = storeItemService.storeItemUpdate(storeName, itemNo, storeAmount);

        int amount = 0;
        if(result>0){

            amount = storeItemService.amountSelect(storeName, itemNo);
            System.out.println("amount =" + amount);
        }

        System.out.println("result : " + result);

        return amount;
    }
}
