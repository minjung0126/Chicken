package com.chicken.project.receive.model.dao;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReceiveMapper {

    List<ReceiveOfficeDTO> selectAllReceive(SelectCriteria selectCriteria);

    List<ReceiveOfficeItemDTO> selectAllReceiveItem(String recCode);

    int selectTotalCount(Map<String, String> searchMap);

    int selectItemTotalCount(Map<String, String> searchMap);

//    List<ReceiveOfficeDTO> selectAllItem(SelectCriteria selectCriteria);

    List<ReceiveOfficeDTO> selectAllItem();

    List<ReceiveOfficeDTO> selectAllReceive();

    List<ReceiveOfficeItemDTO> selectAllReceiveItem();

    List<ItemInfoDTO> selectRegistReceive(List<String> itemNoList);

    int insertReceiveOffice(String empName);

    int insertReceiveOfficeItem(HashMap<String, Object> registHashMap);

    int insertItemHistory(HashMap<String, Object> registHashMap);

    List<ReceiveOfficeDTO> selectMaxCode();

    int updateItem(HashMap<String, Object> registHashMap);
}
