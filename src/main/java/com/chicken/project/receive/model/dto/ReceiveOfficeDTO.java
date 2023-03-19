package com.chicken.project.receive.model.dto;

import com.chicken.project.item.model.dto.ItemCategoryDTO;
import com.chicken.project.item.model.dto.ItemFileDTO;
import com.chicken.project.item.model.dto.ItemInfoDTO;

import java.sql.Date;

public class ReceiveOfficeDTO {

    private String recCode;
    private java.sql.Date recDate;
    private String empId;
    private int sumRecTotalAmount; // 총 입고 수량
    private int countRecCode; // 입고 상품 종류 개수
    private String sumRecTotalMoney; // 총 입고 금액
    private String stringRecDate; // 입고일 ('yyyy-dd-mm')
    private String empName;
    private String maxRecCode;
    private String minRecDate;
    private int rNum;
    private ReceiveOfficeItemDTO receiveOfficeItem;
    private ItemInfoDTO itemInfo;
    private ItemCategoryDTO itemCategory;
    private ItemFileDTO itemFile;
    private RecEmployeeDTO employee;

    public ReceiveOfficeDTO() {
    }

    public ReceiveOfficeDTO(String recCode, Date recDate, String empId, int sumRecTotalAmount, int countRecCode, String sumRecTotalMoney, String stringRecDate, String empName, String maxRecCode, String minRecDate, int rNum, ReceiveOfficeItemDTO receiveOfficeItem, ItemInfoDTO itemInfo, ItemCategoryDTO itemCategory, ItemFileDTO itemFile, RecEmployeeDTO employee) {
        this.recCode = recCode;
        this.recDate = recDate;
        this.empId = empId;
        this.sumRecTotalAmount = sumRecTotalAmount;
        this.countRecCode = countRecCode;
        this.sumRecTotalMoney = sumRecTotalMoney;
        this.stringRecDate = stringRecDate;
        this.empName = empName;
        this.maxRecCode = maxRecCode;
        this.minRecDate = minRecDate;
        this.rNum = rNum;
        this.receiveOfficeItem = receiveOfficeItem;
        this.itemInfo = itemInfo;
        this.itemCategory = itemCategory;
        this.itemFile = itemFile;
        this.employee = employee;
    }

    public String getRecCode() {
        return recCode;
    }

    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getSumRecTotalAmount() {
        return sumRecTotalAmount;
    }

    public void setSumRecTotalAmount(int sumRecTotalAmount) {
        this.sumRecTotalAmount = sumRecTotalAmount;
    }

    public int getCountRecCode() {
        return countRecCode;
    }

    public void setCountRecCode(int countRecCode) {
        this.countRecCode = countRecCode;
    }

    public String getSumRecTotalMoney() {
        return sumRecTotalMoney;
    }

    public void setSumRecTotalMoney(String sumRecTotalMoney) {
        this.sumRecTotalMoney = sumRecTotalMoney;
    }

    public String getStringRecDate() {
        return stringRecDate;
    }

    public void setStringRecDate(String stringRecDate) {
        this.stringRecDate = stringRecDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMaxRecCode() {
        return maxRecCode;
    }

    public void setMaxRecCode(String maxRecCode) {
        this.maxRecCode = maxRecCode;
    }

    public String getMinRecDate() {
        return minRecDate;
    }

    public void setMinRecDate(String minRecDate) {
        this.minRecDate = minRecDate;
    }

    public int getrNum() {
        return rNum;
    }

    public void setrNum(int rNum) {
        this.rNum = rNum;
    }

    public ReceiveOfficeItemDTO getReceiveOfficeItem() {
        return receiveOfficeItem;
    }

    public void setReceiveOfficeItem(ReceiveOfficeItemDTO receiveOfficeItem) {
        this.receiveOfficeItem = receiveOfficeItem;
    }

    public ItemInfoDTO getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfoDTO itemInfo) {
        this.itemInfo = itemInfo;
    }

    public ItemCategoryDTO getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategoryDTO itemCategory) {
        this.itemCategory = itemCategory;
    }

    public ItemFileDTO getItemFile() {
        return itemFile;
    }

    public void setItemFile(ItemFileDTO itemFile) {
        this.itemFile = itemFile;
    }

    public RecEmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(RecEmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ReceiveOfficeDTO{" +
                "recCode='" + recCode + '\'' +
                ", recDate=" + recDate +
                ", empId='" + empId + '\'' +
                ", sumRecTotalAmount=" + sumRecTotalAmount +
                ", countRecCode=" + countRecCode +
                ", sumRecTotalMoney='" + sumRecTotalMoney + '\'' +
                ", stringRecDate='" + stringRecDate + '\'' +
                ", empName='" + empName + '\'' +
                ", maxRecCode='" + maxRecCode + '\'' +
                ", minRecDate='" + minRecDate + '\'' +
                ", rNum=" + rNum +
                ", receiveOfficeItem=" + receiveOfficeItem +
                ", itemInfo=" + itemInfo +
                ", itemCategory=" + itemCategory +
                ", itemFile=" + itemFile +
                ", employee=" + employee +
                '}';
    }
}
