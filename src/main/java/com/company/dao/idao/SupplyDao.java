package com.company.dao.idao;

import com.company.model.entity.Supply;

import java.util.List;

public interface SupplyDao extends BaseDao<Integer, Supply>{
    List<Supply.InnerCodeDesc> getInnerCodesWithDesc();
}
