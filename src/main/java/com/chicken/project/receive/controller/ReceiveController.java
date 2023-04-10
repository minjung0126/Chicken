package com.chicken.project.receive.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;
import com.chicken.project.receive.model.service.ReceiveServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/receive")
public class ReceiveController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ReceiveServiceImpl receiveService;

    @Autowired
    public ReceiveController(ReceiveServiceImpl receiveService){

        this.receiveService = receiveService;
    }

    @GetMapping("/admin/list")
    public ModelAndView receiveList(HttpServletRequest request, ModelAndView mv, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate){

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

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);
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
        int totalCount = receiveService.selectTotalCount(searchMap);
        log.info("[ReceiveController] totalBoardCount : " + totalCount);

        /* 한 페이지에 보여 줄 게시물 수 */
        int limit = 10;		//얘도 파라미터로 전달받아도 된다.

        /* 한 번에 보여질 페이징 버튼의 갯수 */
        int buttonAmount = 5;

        /* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue, startDate, endDate);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        log.info("[ReceiveController] selectCriteria : " + selectCriteria);

        /* 조회해 온다 */
        List<ReceiveOfficeDTO> receiveOfficeList = receiveService.selectAllReceive(selectCriteria);

        String minRecDate = receiveService.selectMinRecDate();

        log.info("[ReceiveController] receiveOfficeList : " + receiveOfficeList);
        log.info("minRecDate :" + minRecDate);

        mv.addObject("receiveOfficeList", receiveOfficeList);
        mv.addObject("minRecDate", minRecDate);
        mv.addObject("selectCriteria", selectCriteria);
        log.info("[ReceiveController] SelectCriteria : " + selectCriteria);

        List<List<ReceiveOfficeItemDTO>> receiveOffice = new ArrayList<>();
        for(int i = 0; i < receiveOfficeList.size(); i++){

            List<ReceiveOfficeItemDTO> receiveOfficeItemList = receiveService.selectAllReceiveItem(receiveOfficeList.get(i).getRecCode());
            receiveOffice.add(receiveOfficeItemList);
        }

        for(int i = 0; i < receiveOfficeList.size(); i++){
            for(int j = 0; j < receiveOffice.get(i).size(); j++){
                System.out.println("출력 " + receiveOffice.get(i).get(j));
            }
        }

        mv.addObject("receiveOffice", receiveOffice);

        mv.setViewName("receive/admin/admin_receive");
        log.info("[ReceiveController] =========================================================");

        return mv;
    }

    @GetMapping(value = "/admin/list/regist")
    public ModelAndView receiveRegist(HttpServletRequest request, ModelAndView mv){

        /* 품목 리스트 전체 조회 (품목 불러오기 레이어 팝업) */
        List<ReceiveOfficeDTO> receiveOfficeItemList = receiveService.selectAllItem();

        log.info("[ReceiveController] receiveOfficeItemList : " + receiveOfficeItemList);

        mv.addObject("receiveOfficeItemList", receiveOfficeItemList);

        mv.setViewName("receive/admin/admin_receive_new");

        return mv;
    }

    @PostMapping(value = "/admin/list/regist")
    public ModelAndView receiveRegistItem(HttpServletRequest request, ModelAndView mv, @RequestParam("itemNoList") String itemNo){

        /* 품목 리스트 전체 조회 */
        List<ReceiveOfficeDTO> receiveOfficeItemList = receiveService.selectAllItem();

        /* 선택한 품목 리스트 조회 */
        String[] itemNoArray = itemNo.split(",");
        List<String> itemNoList = new ArrayList<>();

        for(int i = 0; i < itemNoArray.length; i++){

            itemNoList.add(itemNoArray[i]);
        }
        System.out.println(itemNoList);
        
        List<ItemInfoDTO> registReceiveList = receiveService.selectRegistReceive(itemNoList);

        mv.addObject("receiveOfficeItemList", receiveOfficeItemList);
        mv.addObject("registReceiveList", registReceiveList);

        mv.setViewName("receive/admin/admin_receive_new");

        return mv;
    }

    @PostMapping("/admin/regist")
    public String receiveRegist(ModelAndView mv, RedirectAttributes rttr, @RequestParam("registList") String registItemNo, @RequestParam("empName") String empName) throws ReceiveInsertException, ReceiveUpdateException {

        log.info("[empName]" + empName);
        receiveService.insertReceiveOffice(empName);

        System.out.println(registItemNo);
        String[] registList = registItemNo.split(",");
        HashMap<String, Object> registHashMap = new HashMap<>();
        for(int i = 0; i < registList.length; i++){

            String[] registItem = registList[i].split(" ");

            registHashMap.put("itemNo", registItem[0]);
            registHashMap.put("amount", registItem[1]);
            receiveService.insertReceiveOfficeItem(registHashMap);
            receiveService.insertItemHistory(registHashMap);
            List<ReceiveOfficeDTO> recCode = receiveService.selectMaxCode();
            System.out.println(recCode);
            registHashMap.put("recCode", recCode.get(0).getMaxRecCode());

            receiveService.updateItem(registHashMap);
            System.out.println(registHashMap.get("recCode"));
        }

        rttr.addFlashAttribute("message", "입고 등록에 성공하셨습니다!");

        mv.addObject("registHashMap", registHashMap);

        return "redirect:/receive/admin/list";
    }

}
