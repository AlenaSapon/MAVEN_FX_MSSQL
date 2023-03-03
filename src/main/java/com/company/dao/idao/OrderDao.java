package com.company.dao.idao;

import com.company.model.entity.Order;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface OrderDao extends BaseDao<Integer, Order> {
    BigDecimal calculateAmount(Integer syntCode);

    boolean deleteWithDescription(Integer id) throws SQLException;

}
