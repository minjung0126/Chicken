package com.chicken.project.receive.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import com.chicken.project.receive.model.dao.ReceiveMapper;
import com.chicken.project.receive.model.dto.ReceiveOfficeDTO;
import com.chicken.project.receive.model.dto.ReceiveOfficeItemDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiveServiceImpl implements ReceiveService{

    private final ReceiveMapper receiveMapper;

    @Autowired
    public ReceiveServiceImpl(ReceiveMapper receiveMapper){

        this.receiveMapper = receiveMapper;
    }

    @Override
    public int selectTotalCount(Map<String, String> searchMap) {

        int result = receiveMapper.selectTotalCount(searchMap);

        return result;
    }

    @Override
    public List<ReceiveOfficeDTO> selectAllReceive(SelectCriteria selectCriteria) {

        List<ReceiveOfficeDTO> receiveOfficeList = receiveMapper.selectAllReceive(selectCriteria);
        return receiveOfficeList;
    }

    @Override
    public List<ReceiveOfficeItemDTO> selectAllReceiveItem(String recCode) {

        return receiveMapper.selectAllReceiveItem(recCode);
    }

    @Override
    public int selectItemTotalCount(Map<String, String> searchMap) {

        int result = receiveMapper.selectItemTotalCount(searchMap);

        return result;
    }

//    @Override
//    public List<ReceiveOfficeDTO> selectAllItem(SelectCriteria selectCriteria) {
//
//        return receiveMapper.selectAllItem(selectCriteria);
//    }

    @Override
    public List<ReceiveOfficeDTO> selectAllItem() {

        return receiveMapper.selectAllItem();
    }

    @Override
    public List<ItemInfoDTO> selectRegistReceive(List<String> itemNoList) {

        return receiveMapper.selectRegistReceive(itemNoList);
    }

    @Override
    @Transactional
    public void insertReceiveOffice(String empName) throws ReceiveInsertException{
        
        int result = receiveMapper.insertReceiveOffice(empName);

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }
    }

    @Override
    @Transactional
    public void insertReceiveOfficeItem(HashMap<String, Object> registHashMap) throws ReceiveInsertException{

        int result = receiveMapper.insertReceiveOfficeItem(registHashMap);

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }
    }

    @Override
    @Transactional
    public void insertItemHistory(HashMap<String, Object> registHashMap) throws ReceiveInsertException{

        int result = receiveMapper.insertItemHistory(registHashMap);

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }
    }

    @Override
    public List<ReceiveOfficeDTO> selectMaxCode() {

        return receiveMapper.selectMaxCode();
    }

    @Override
    @Transactional
    public void updateItem(HashMap<String, Object> registHashMap) throws ReceiveUpdateException {

        int result = receiveMapper.updateItem(registHashMap);

        if(!(result > 0)) {
            throw new ReceiveUpdateException("재고 수정에 실패하셨습니다.");
        }
    }
}
