package com.chicken.project.storeReceive.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.receive.ReceiveInsertException;
import com.chicken.project.exception.receive.ReceiveUpdateException;
import com.chicken.project.storeReceive.model.dto.RecReleaseDTO;
import com.chicken.project.storeReceive.model.dto.RecReleaseItemDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StoreReceiveService {


    int selectTotalCount(Map<String, String> searchMap);

    List<RecReleaseDTO> selectAllRelease(SelectCriteria selectCriteria);

    List<RecReleaseItemDTO> selectAllItem(String relCode);

    @Transactional
    int insertReceiveStore(HashMap<String, Object> rec) throws ReceiveInsertException;

    @Transactional
    void insertTaxBill() throws ReceiveInsertException;

    @Transactional
    void insertTsBill() throws ReceiveInsertException;

    @Transactional
    void insertReceiveStoreItem(Map<String, Object> recList) throws ReceiveInsertException;

    int selectOneItem(Object recItemNo, String storeName);

    @Transactional
    void updateOneItem(Map<String, Object> recList) throws ReceiveUpdateException;

    @Transactional
    void insertOneItem(Map<String, Object> recList) throws ReceiveInsertException;

    String selectMinOrderDate(String storeName);
}
