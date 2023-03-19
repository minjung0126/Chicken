package com.chicken.project.storeReceive.model.dao;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.storeReceive.model.dto.RecReleaseDTO;
import com.chicken.project.storeReceive.model.dto.RecReleaseItemDTO;
import com.chicken.project.storeReceive.model.dto.RecStoreOrderDTO;
import com.chicken.project.storeReceive.model.dto.ReceiveStoreDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface StoreReceiveMapper {

    int selectTotalCount(Map<String, String> searchMap);

    List<RecReleaseDTO> selectAllRelease(SelectCriteria selectCriteria);

    List<RecReleaseItemDTO> selectAllItem(String relCode);

    int insertReceiveStore(HashMap<String, Object> rec);

    int insertTaxBill();

    int insertTsBill();

    int insertReceiveStoreItem(Map<String, Object> recList);

    int selectOneItem(Object recItemNo, String storeName);

    int updateOneItem(Map<String, Object> recList);

    int insertOneItem(Map<String, Object> recList);

    String selectMinOrderDate(String storeName);
}
