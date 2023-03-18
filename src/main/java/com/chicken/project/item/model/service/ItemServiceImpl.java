package com.chicken.project.item.model.service;

import com.chicken.project.common.paging.SelectCriteria;
import com.chicken.project.exception.item.ItemInsertException;
import com.chicken.project.exception.item.ItemUpdateException;
import com.chicken.project.item.model.dao.ItemMapper;
import com.chicken.project.item.model.dto.ItemCategoryDTO;
import com.chicken.project.item.model.dto.ItemFileDTO;
import com.chicken.project.item.model.dto.ItemInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper){

        this.itemMapper = itemMapper;
    }

    @Override
    public int selectTotalCount(Map<String, String> searchMap) {

        int result = itemMapper.selectTotalCount(searchMap);

        return result;
    }

    @Override
    public List<ItemInfoDTO> selectAllItem(SelectCriteria selectCriteria) {

        List<ItemInfoDTO> itemList = itemMapper.selectAllItem(selectCriteria);
        return itemList;
    }

    @Override
    public List<ItemCategoryDTO> selectPreCategory() {

        List<ItemCategoryDTO> itemPreCategoryList = itemMapper.selectPreCategory();
        return itemPreCategoryList;
    }

    @Override
    public List<ItemCategoryDTO> selectCategory() {

        List<ItemCategoryDTO> itemCategoryList = itemMapper.selectCategory();
        return itemCategoryList;
    }

    @Override
    @Transactional
    public int insertItem(ItemInfoDTO item) throws ItemInsertException {

        int result = itemMapper.insertItem(item);

        if(!(result > 0)){
            throw new ItemInsertException("상품 등록에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int deleteItem(String itemNo) throws ItemUpdateException {

        int result = itemMapper.deleteItem(itemNo);

        if(!(result > 0)){
            throw new ItemUpdateException("상품 삭제에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int deleteItemFile(String itemNo) throws ItemUpdateException {

        int result = itemMapper.deleteItemFile(itemNo);

        if(!(result > 0)){
            throw new ItemUpdateException("상품 이미지 삭제에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int insertFileRegist(ItemFileDTO itemFile) throws ItemUpdateException{

        int result = itemMapper.insertFileRegist(itemFile);

        if(!(result > 0)){
            throw new ItemUpdateException("상품 이미지 등록에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int insertItemHistory() throws ItemInsertException{

        int result = itemMapper.insertItemHistory();

        if(!(result > 0)){
            throw new ItemInsertException("상품 등록에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    public ItemInfoDTO selectOneItem(String itemNoInput) {

        ItemInfoDTO oneItem = itemMapper.selectOneItem(itemNoInput);

        return oneItem;
    }

    @Override
    public List<ItemInfoDTO> selectMainItem() {

        List<ItemInfoDTO> itemList = itemMapper.selectMainItem();

        return itemList;
    }

    @Override
    @Transactional
    public int updateItem(ItemInfoDTO item) throws ItemUpdateException {

        int result = itemMapper.updateItem(item);

        if (!(result > 0)) {

            throw new ItemUpdateException("재고 수정에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int deleteItemFile2(int itemNo) throws ItemUpdateException{

        int result = itemMapper.deleteItemFile2(itemNo);

        if(!(result > 0)){
            throw new ItemUpdateException("상품 이미지 삭제에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    @Transactional
    public int insertItemFile(ItemFileDTO itemFile) throws ItemInsertException{

        int result = itemMapper.insertItemFile(itemFile);

        if(!(result > 0)){
            throw new ItemInsertException("상품 등록에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    public int selectItemFileCheck(String itemNo) {

        int count = itemMapper.selectItemFileCheck(itemNo);

        return count;
    }

    @Override
    public int selectTotalItemCount() {

        int result = itemMapper.selectTotalItemCount();

        return result;
    }

    @Override
    @Transactional
    public int updateItemFile(ItemFileDTO itemFile) throws ItemUpdateException{

        int result = itemMapper.updateItemFile(itemFile);

        if (!(result > 0)) {

            throw new ItemUpdateException("파일 수정에 실패하셨습니다.");
        }

        return result;
    }

    @Override
    public ItemFileDTO selectOneItemFile(int itemNo) {

        ItemFileDTO oneFile = itemMapper.selectOneItemFile(itemNo);

        return oneFile;
    }
}
