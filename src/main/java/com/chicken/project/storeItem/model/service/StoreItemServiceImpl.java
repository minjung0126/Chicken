package com.chicken.project.storeItem.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.storeItem.model.dao.StoreItemMapper;
import com.chicken.project.storeItem.model.dto.StoreItemListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreItemServiceImpl implements StoreItemService {

    public final StoreItemMapper storeItemMapper;

    @Autowired
    public StoreItemServiceImpl (StoreItemMapper storeItemMapper){

        this.storeItemMapper = storeItemMapper;
    }

    @Override
    public List<StoreItemListDTO> itemListSelect(SelectCriteria selectCriteria) {

        List<StoreItemListDTO> itemList = storeItemMapper.itemListSelect(selectCriteria);

        return itemList;
    }

    @Override
    public int selectTotalCount(Map<String, String> searchMap) {

        int totalCount = storeItemMapper.selectTotalCount(searchMap);

        return totalCount;
    }
}