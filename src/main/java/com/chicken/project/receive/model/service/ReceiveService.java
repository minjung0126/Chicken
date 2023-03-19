package com.chicken.project.receive.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReceiveService {

    public int selectTotalCount(Map<String, String> searchMap);

    List<ReceiveOfficeDTO> selectAllReceive(SelectCriteria selectCriteria);

    List<ReceiveOfficeItemDTO> selectAllReceiveItem(String recCode);

    int selectItemTotalCount(Map<String, String> searchMap);

//    List<ReceiveOfficeDTO> selectAllItem(SelectCriteria selectCriteria);

    List<ReceiveOfficeDTO> selectAllItem();

    List<ItemInfoDTO> selectRegistReceive(List<String> itemNoList);

    @Transactional
    void insertReceiveOffice(String empName) throws ReceiveInsertException;

    @Transactional
    void insertReceiveOfficeItem(HashMap<String, Object> registHashMap) throws ReceiveInsertException;

    @Transactional
    void insertItemHistory(HashMap<String, Object> registHashMap) throws ReceiveInsertException;

    List<ReceiveOfficeDTO> selectMaxCode();

    @Transactional
    void updateItem(HashMap<String, Object> registHashMap) throws ReceiveUpdateException;


    String selectMinRecDate();
}
