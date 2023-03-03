package com.company.dao.idao;

import com.company.model.entity.OrderDesc;

import java.sql.SQLException;
import java.util.List;

public interface OrderDescDao extends BaseDao<OrderDesc.CompoundKey, OrderDesc> {

    OrderDesc update(OrderDesc orderDesc, OrderDesc.CompoundKey compoundKey) throws SQLException;

    List<OrderDesc> findAllByOrderKey(Integer key) throws SQLException;

}
