package com.chicken.project.receive.controller;

import com.chicken.project.common.paging.Pagenation;
import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;
import com.chicken.project.receive.model.service.ReceiveService;
import com.chicken.project.release.model.dto.ItemInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/receive")
public class ReceiveController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ReceiveService receiveService;

    @Autowired
    public ReceiveController(ReceiveService receiveService){

        this.receiveService = receiveService;
    }

    @GetMapping("/admin/list")
    public ModelAndView receiveList(HttpServletRequest request, ModelAndView mv){

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
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        log.info("[ReceiveController] selectCriteria : " + selectCriteria);

        /* 조회해 온다 */
        List<ReceiveOfficeDTO> receiveOfficeList = receiveService.selectAllReceive(selectCriteria);

        log.info("[ReceiveController] receiveOfficeList : " + receiveOfficeList);

        mv.addObject("receiveOfficeList", receiveOfficeList);
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

    @GetMapping("/admin/list/new")
    public ModelAndView receiveNew(HttpServletRequest request, ModelAndView mv, @RequestParam ){

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

        log.info("[ReceiveController] 컨트롤러에서 검색조건 확인하기 : " + searchMap);
        /*
         * 전체 게시물 수가 필요하다.
         * 데이터베이스에서 먼저 전체 게시물 수를 조회해올 것이다.
         * 검색조건이 있는 경우 검색 조건에 맞는 전체 게시물 수를 조회한다.
         */
        int totalCount = receiveService.selectItemTotalCount(searchMap);
        log.info("[ReceiveController] totalBoardCount : " + totalCount);

        /* 한 페이지에 보여 줄 게시물 수 */
        int limit = 5;		//얘도 파라미터로 전달받아도 된다.

        /* 한 번에 보여질 페이징 버튼의 갯수 */
        int buttonAmount = 5;

        /* 페이징 처리를 위한 로직 호출 후 페이징 처리에 관한 정보를 담고 있는 인스턴스를 반환받는다. */
        SelectCriteria selectCriteria = null;

        if(searchCondition != null && !"".equals(searchCondition)) {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount, searchCondition, searchValue);
        } else {
            selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);
        }

        log.info("[ReceiveController] selectCriteria : " + selectCriteria);

        /* 조회해 온다 */
        List<ReceiveOfficeDTO> receiveOfficeItemList = receiveService.selectAllItem(selectCriteria);


        List<Integer> receiveItemNo =
        List<ReceiveOfficeItemDTO> receiveOfficeChooseItemList = receiveService.selectChooseItem(receiveItemNo);

        log.info("[ReceiveController] receiveOfficeItemList : " + receiveOfficeItemList);

        mv.addObject("receiveOfficeItemList", receiveOfficeItemList);
        mv.addObject("selectCriteria", selectCriteria);
        log.info("[ReceiveController] SelectCriteria : " + selectCriteria);

        mv.setViewName("receive/admin/admin_receive_new");

        return mv;
    }
}
