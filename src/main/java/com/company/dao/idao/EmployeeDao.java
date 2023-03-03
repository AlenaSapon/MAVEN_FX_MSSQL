package com.company.dao.idao;

import com.company.model.entity.Employee;

import java.util.List;

public interface EmployeeDao extends BaseDao<Integer, Employee>{
    List<Integer> getAllStaffCodes();
}
