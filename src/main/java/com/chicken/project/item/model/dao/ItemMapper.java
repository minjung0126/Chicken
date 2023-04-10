package com.chicken.project.item.model.dao;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.item.model.dto.ItemCategoryDTO;
import com.chicken.project.item.model.dto.ItemFileDTO;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    int selectTotalCount(Map<String, String> searchMap);
    List<ItemInfoDTO> selectAllItem(SelectCriteria selectCriteria);
    List<ItemCategoryDTO> selectPreCategory();
    List<ItemCategoryDTO> selectCategory();
    int insertItem(ItemInfoDTO item);
    int deleteItem(String itemNo);
    int deleteItemFile(String itemNo);
    int insertFileRegist(ItemFileDTO itemFile);
    int insertItemHistory();
    ItemInfoDTO selectOneItem(String itemNoInput);
    List<ItemInfoDTO> selectMainItem();
    int updateItem(ItemInfoDTO item);
    int deleteItemFile2(int item);
    int insertItemFile(ItemFileDTO itemFile);
    int selectItemFileCheck(String itemNo);
    int selectTotalItemCount();
    int updateItemFile(ItemFileDTO itemFile);
    ItemFileDTO selectOneItemFile(int itemNo);
}
