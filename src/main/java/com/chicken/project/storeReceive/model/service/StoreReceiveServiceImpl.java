package com.chicken.project.storeReceive.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.storeReceive.model.dao.StoreReceiveMapper;
import com.chicken.project.storeReceive.model.dto.RecReleaseDTO;
import com.chicken.project.storeReceive.model.dto.RecReleaseItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreReceiveServiceImpl implements StoreReceiveService{

    private final StoreReceiveMapper storeReceiveMapper;

    @Autowired
    public StoreReceiveServiceImpl(StoreReceiveMapper storeReceiveMapper){

        this.storeReceiveMapper = storeReceiveMapper;
    }


    @Override
    public int selectTotalCount(Map<String, String> searchMap) {

        int result = storeReceiveMapper.selectTotalCount(searchMap);

        return result;
    }

    @Override
    public List<RecReleaseDTO> selectAllRelease(SelectCriteria selectCriteria) {

        List<RecReleaseDTO> releaseList = storeReceiveMapper.selectAllRelease(selectCriteria);
        return releaseList;
    }

    @Override
    public List<RecReleaseItemDTO> selectAllItem(String relCode) {

        List<RecReleaseItemDTO> itemList = storeReceiveMapper.selectAllItem(relCode);

        return itemList;
    }

    @Override
    @Transactional
    public int insertReceiveStore(HashMap<String, Object> rec) throws ReceiveInsertException{

        int result = storeReceiveMapper.insertReceiveStore(rec);

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public void insertTaxBill() throws ReceiveInsertException{

        int result = storeReceiveMapper.insertTaxBill();

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }

    }

    @Override
    @Transactional
    public void insertTsBill() throws ReceiveInsertException{

        int result = storeReceiveMapper.insertTsBill();

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }
    }

    @Override
    @Transactional
    public void insertReceiveStoreItem(Map<String, Object> recList) throws ReceiveInsertException{

        int result = storeReceiveMapper.insertReceiveStoreItem(recList);

        if(!(result > 0)) {
            throw new ReceiveInsertException("입고 등록에 실패하셨습니다.");
        }
    }

    @Override
    public int selectOneItem(Object recItemNo, String storeName) {

        int result = storeReceiveMapper.selectOneItem(recItemNo, storeName);
        return result;
    }

    @Override
    @Transactional
    public void updateOneItem(Map<String, Object> recList) throws ReceiveUpdateException{

        int result = storeReceiveMapper.updateOneItem(recList);

        if(!(result > 0)) {
            throw new ReceiveUpdateException("품목 수정에 실패하셨습니다.");
        }
    }

    @Override
    @Transactional
    public void insertOneItem(Map<String, Object> recList) throws ReceiveInsertException{

        int result = storeReceiveMapper.insertOneItem(recList);

        if(!(result > 0)) {
            throw new ReceiveInsertException("품목 등록에 실패하셨습니다.");
        }
    }

    @Override
    public String selectMinOrderDate(String storeName) {

        return storeReceiveMapper.selectMinOrderDate(storeName);
    }
}
