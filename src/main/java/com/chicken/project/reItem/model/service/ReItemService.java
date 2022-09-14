package com.chicken.project.reItem.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.reItem.model.dao.ReItemMapper;
import com.chicken.project.reItem.model.dto.ReItemDTO;
import com.chicken.project.reItem.model.dto.ReListDTO;
import com.chicken.project.reItem.model.dto.StoreItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("reItemService")
public class ReItemService {

    public ReItemMapper reItemMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ReItemService(ReItemMapper reItemMapper) { this.reItemMapper = reItemMapper; }

    // 페이징
    public int selectTotalCount(Map<String, String> searchMap) {

        int result = reItemMapper.selectTotalCount(searchMap);

        return result;
    }

    // 가맹점 반품서 리스트보기
    public List<ReListDTO> selectReList(SelectCriteria selectCriteria) {


        List<ReListDTO> reList = reItemMapper.selectReList(selectCriteria);

        return reList;
    }

    public List<ReListDTO> selectReturnList(SelectCriteria selectCriteria) {

        List<ReListDTO> returnList = reItemMapper.selectReturnList(selectCriteria);

        return returnList;
    }

    // 본사 반품 상세보기
    public ReItemDTO selectReturnItem(String rNo) {

        ReItemDTO reItem = reItemMapper.selectReturnItem(rNo);

        return reItem;
    }


    public List<ReItemDTO> selectReturnItems(String rNo) {

        List<ReItemDTO> reItems = reItemMapper.selectReItems(rNo);

        return reItems;
    }

    public List<StoreItemDTO> selectItem(String storeName) {

        List<StoreItemDTO> storeItems = reItemMapper.selectItem(storeName);

        return storeItems;
    }

    @Transactional
    public int insertReItem(List<ReItemDTO> insertItem, String storeName) {

        int result = 0;

        result = reItemMapper.insertReturnItems(insertItem.get(0));

        if(result > 0){ //insertReItem1 성공 시 insertReItem2 실행

            log.info("result 확인 : " + result);
            result = 0; //초기화

            Map<String, Object> maps = new HashMap<>();

            for(int i =0; i < insertItem.size(); i++){

                maps.put("returnCount", insertItem.get(i).getReturnCount());
                maps.put("itemNo", insertItem.get(i).getItemNo());
                maps.put("storeName", storeName);
                result = reItemMapper.insertRItem(maps);
            }

            if(result > 0){

                result = 0;

                result = reItemMapper.insertProgress(insertItem.get(0));
            }
        }

        return result;
    }

}
