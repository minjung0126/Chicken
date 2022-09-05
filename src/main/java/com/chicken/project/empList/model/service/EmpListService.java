package com.chicken.project.empList.model.service;

import com.chicken.project.member.model.dto.EmployeeDTO;

import java.util.List;

public interface EmpListService {
    /* 직원 정보 리스트 조회 */
    List<EmployeeDTO> selectAllEmployee();

    /* 직원 정보 등록 */
    int registEmp(EmployeeDTO emp);
}
