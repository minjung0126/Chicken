package com.chicken.project.storeReceive.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.member.model.dto.StoreImpl;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;
import com.chicken.project.storeItem.model.dto.StoreItemListDTO;
import com.chicken.project.storeReceive.model.dto.RecReleaseDTO;
import com.chicken.project.storeReceive.model.dto.RecReleaseItemDTO;
import com.chicken.project.storeReceive.model.dto.RecStoreOrderDTO;
import com.chicken.project.storeReceive.model.dto.ReceiveStoreDTO;
import com.chicken.project.storeReceive.model.service.StoreReceiveServiceImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/storeReceive")
public class StoreReceiveController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final StoreReceiveServiceImpl storeReceiveService;

    @Autowired
    public StoreReceiveController(StoreReceiveServiceImpl storeReceiveService){

        this.storeReceiveService = storeReceiveService;
    }

    @GetMapping("/user/list")
    public ModelAndView storeReceiveList(HttpServletRequest request, ModelAndView mv, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate, @AuthenticationPrincipal StoreImpl store){

        log.info("");
        log.info("");
        log.info("[ReceiveController] =========================================================");
        /*
         * 목록보기를 눌렀을 시 가장 처음에 보여지는 페이지는 1페이지이다.
         * 파라미터로 전달되는 페이지가 있는 경우 currentPage는 파라미터로 전달받은 페이지 수 이다.
         */
        String currentPage = request.getParameter("currentPage");
        int pageNo = 1;

        if(currentPage != null && !"".equals(currentPage)) {
            pageNo = Integer.parseInt(currentPage);
        }

        String searchCondition = request.getParameter("searchCondition");
        String searchValue = request.getParameter("searchValue");
        String storeName = store.getStoreName();


        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
        searchMap.put("storeName", storeName);
        if(startDate != null && endDate != null){
            searchMap.put("startDate", startDate);
            searchMap.put("endDate", endDate);
        }

        log.info("[ReceiveController] 컨트롤러에서 검색조건 확인하기 : " + searchMap);
        /*
         * 전체 게시물 수가 필요하다.
         * 데이터베이스에서 먼저 전체 게시물 수를 조회해올 것이다.
         * 검색조건이 있는 경우 검색 조건에 맞는 전체 게시물 수를 조회한다.
         */
        int totalCount = storeReceiveService.selectTotalCount(searchMap);
        log.info("[ReceiveController] totalBoardCount : " + totalCount);

        /* 한 페이지에 보여 줄 게시물 수 */
        int limit;		//얘도 파라미터로 전달받아도 된다.
        if(searchCondition != null && !"".equals(searchCondition)) {
            limit = totalCount;
        } else{
            limit = 10;
        }

        /* 한 번에 보여질 페이징 버튼의 갯수 */
        int buttonAmount = 5;

        /* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue, startDate, endDate, storeName);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, storeName);
        }

        log.info("[ReceiveController] selectCriteria : " + selectCriteria);

        /* 조회해 온다 */
        List<RecReleaseDTO> releaseList = storeReceiveService.selectAllRelease(selectCriteria);

        log.info("[ReceiveController] releaseList : " + releaseList);

        mv.addObject("releaseList", releaseList);
        mv.addObject("selectCriteria", selectCriteria);
        log.info("[ReceiveController] SelectCriteria : " + selectCriteria);

        List<List<RecReleaseItemDTO>> receiveItem = new ArrayList<>();
        for(int i = 0; i < releaseList.size(); i++){
            System.out.println(releaseList.get(i).getRelCode());
            List<RecReleaseItemDTO> itemList = storeReceiveService.selectAllItem(releaseList.get(i).getRelCode());
            receiveItem.add(itemList);
        }

        for(int i = 0; i < releaseList.size(); i++){
            for(int j = 0; j < receiveItem.get(i).size(); j++){
                System.out.println("출력 " + receiveItem.get(i).get(j));
            }
        }

        mv.addObject("receiveItem", receiveItem);


        mv.setViewName("receive/user/user_receive");

        return mv;
    }

    @PostMapping(value = "/user/regist")
    public String receiveRegist(ModelAndView mv, RedirectAttributes rttr, @RequestParam("receiveList") String receiveList, @AuthenticationPrincipal StoreImpl store) throws ReceiveInsertException, ReceiveUpdateException {

        String storeName = store.getStoreName();
        String storeAccount = store.getStoreAccount();
        log.info("[receiveList]" + receiveList);

        JsonParser jsonParse = new JsonParser();

        JsonArray receiveArr = (JsonArray) jsonParse.parse(receiveList);

        System.out.println("receiveArr = " + receiveArr);
        List<Map<String, Object>> recList = new ArrayList<>();
        for(int i = 0; i < receiveArr.size(); i++){
            JsonObject receiveObject = (JsonObject) receiveArr.get(i);
            Integer sales = receiveObject.get("sales").getAsInt();
            Integer recAmount = receiveObject.get("recAmount").getAsInt();
            System.out.println("sales = " + sales);
            System.out.println("recAmount = " + recAmount);
            Map<String, Object> map = new HashMap<>();
            map.put("recItemNo", receiveObject.get("recItemNo").getAsInt());
            map.put("recAmount", receiveObject.get("recAmount").getAsInt());
            map.put("sales", receiveObject.get("sales"));
            map.put("categoryNo", receiveObject.get("categoryNo").getAsInt());
            map.put("recOrderNo", receiveObject.get("recOrderNo").getAsInt());
            map.put("recMoney", sales * recAmount);
            map.put("recSupply", Math.round((sales * recAmount) / 1.1));
            map.put("recTax", (sales * recAmount) - Math.round((sales * recAmount) / 1.1));
            map.put("storeName", storeName);
            map.put("storeAccount", storeAccount);
            recList.add(map);
        }


        int recTotalAmount = 0;
        int recTotalMoney = 0;

        for(int i = 0; i < recList.size(); i++){
            int recAmount = (int) recList.get(i).get("recAmount");
            int recMoney = (int) recList.get(i).get("recMoney");
            recTotalAmount += recAmount;
            recTotalMoney += recMoney;
        }
        System.out.println("토탈출력" + recTotalAmount + " " + recTotalMoney);

        System.out.println("recList 출력" + recList);
        System.out.println("orderNo출력" +recList.get(0).get("recOrderNo"));

        // receiveStore
        HashMap<String, Object> rec = new HashMap<>();
        rec.put("orderNo", recList.get(0).get("recOrderNo"));
        rec.put("recTotalAmount", recTotalAmount);
        rec.put("recTotalMoney", recTotalMoney);
        rec.put("storeName", storeName);
        System.out.println(rec + "출력");

        int result = storeReceiveService.insertReceiveStore(rec);
        if(result > 0) {
            storeReceiveService.insertTaxBill();
            storeReceiveService.insertTsBill();
            // receiveStoreItem
            for(int i = 0; i < recList.size(); i++){

                // 입고 품목에 insert
                storeReceiveService.insertReceiveStoreItem(recList.get(i));

                // 품목 등록 여부에 따라 insert, update
                int result2 = storeReceiveService.selectOneItem(recList.get(i).get("recItemNo"), storeName);
                if(result2 > 0){
                    storeReceiveService.updateOneItem(recList.get(i));
                } else {
                    storeReceiveService.insertOneItem(recList.get(i));
                }
            }
        }

        return "redirect:/storeReceive/user/list";
    }
}
