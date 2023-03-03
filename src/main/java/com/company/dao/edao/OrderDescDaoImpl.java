package com.company.dao.edao;

import com.company.dao.DBConnector;
import com.company.dao.idao.OrderDescDao;
import com.company.model.entity.Entity;
import com.company.model.entity.OrderDesc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDescDaoImpl implements OrderDescDao {

    private static final String SQL_SELECT_ALL_ORDERS_DESC = "SELECT synt_code, inner_code, vend_code, suppl_description, isnull(price, 0.00), qty, isnull(amount, 0.00) FROM ORDERS_DESC";
    private static final String SQL_SELECT_BY_ORDER = "SELECT synt_code, inner_code, vend_code, suppl_description, isnull(price, 0.00), qty, isnull(amount, 0.00) FROM ORDERS_DESC WHERE SYNT_CODE=?";
    private static final String SQL_SELECT_ORDERS_DESC_BY_COMPOUND_KEY = "SELECT synt_code, inner_code, vend_code, suppl_description, isnull(price, 0.00), qty, isnull(amount, 0.00) FROM ORDERS_DESC WHERE SYNT_CODE=? and INNER_CODE=? and VEND_CODE=?";
    private static final String SQL_DELETE_ORDERS_DESC_BY_COMPOUND_KEY = "DELETE FROM ORDERS_DESC WHERE SYNT_CODE=? and INNER_CODE=? and VEND_CODE=?";
    private static final String SQL_INSERT_ORDERS_DESC = "INSERT INTO ORDERS_DESC VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE_ORDERS_DESC ="UPDATE ORDERS_DESC SET SUPPL_DESCRIPTION=?," +
            " PRICE=?, QTY=?, AMOUNT=? WHERE SYNT_CODE=? and INNER_CODE=? and VEND_CODE=?";
    private static final String SQL_UPDATE_ORDERS_DESC_KEY ="UPDATE ORDERS_DESC SET SUPPL_DESCRIPTION=?," +
            " PRICE=?, QTY=?, AMOUNT=?, INNER_CODE=?,VEND_CODE=?  WHERE SYNT_CODE=? and INNER_CODE=? and VEND_CODE=?";



    @Override
    public List<OrderDesc> findAll() throws SQLException {
        ArrayList<OrderDesc> list=new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ALL_ORDERS_DESC);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                list.add(generate(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return list;
    }

    @Override
    public OrderDesc findEntityById(OrderDesc.CompoundKey id) throws SQLException {
        OrderDesc orderDesc =null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ORDERS_DESC_BY_COMPOUND_KEY);
            statement.setInt(1,id.getSyntCode());
            statement.setInt(2,id.getInnerCode());
            statement.setInt(3,id.getVendCode());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                orderDesc=generate(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return orderDesc;
    }

    @Override
    public boolean delete(OrderDesc orderDesc) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(OrderDesc.CompoundKey id) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_DELETE_ORDERS_DESC_BY_COMPOUND_KEY);
            statement.setInt(1,id.getSyntCode());
            statement.setInt(2, id.getInnerCode());
            statement.setInt(3, id.getVendCode());
            isDone=statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return isDone;
    }

    @Override
    public boolean create(OrderDesc orderDesc) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_INSERT_ORDERS_DESC);
            statement.setInt(1,orderDesc.getCompoundKey().getSyntCode());
            statement.setInt(2,orderDesc.getCompoundKey().getInnerCode());
            statement.setInt(3,orderDesc.getCompoundKey().getVendCode());
            statement.setString(4, orderDesc.getSupplyDesc());
            statement.setBigDecimal(5, orderDesc.getPrice());
            statement.setInt(6, orderDesc.getQty());
            statement.setBigDecimal(7, orderDesc.getAmount());
            isDone=statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return isDone;
    }

    @Override
    public OrderDesc update(OrderDesc orderDesc) throws SQLException {
        OrderDesc result=null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_UPDATE_ORDERS_DESC);
            statement.setString(1, orderDesc.getSupplyDesc());
            statement.setBigDecimal(2, orderDesc.getPrice());
            statement.setInt(3, orderDesc.getQty());
            statement.setBigDecimal(4, orderDesc.getAmount());
            statement.setInt(5,orderDesc.getCompoundKey().getSyntCode());
            statement.setInt(6,orderDesc.getCompoundKey().getInnerCode());
            statement.setInt(7,orderDesc.getCompoundKey().getVendCode());
            statement.executeUpdate();
            result=findEntityById(orderDesc.getCompoundKey());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }


    @Override
    public OrderDesc update(OrderDesc orderDesc,  OrderDesc.CompoundKey compoundKey) throws SQLException {
        OrderDesc result=null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_UPDATE_ORDERS_DESC_KEY);
            statement.setString(1, orderDesc.getSupplyDesc());
            statement.setBigDecimal(2, orderDesc.getPrice());
            statement.setInt(3, orderDesc.getQty());
            statement.setBigDecimal(4, orderDesc.getAmount());
            statement.setInt(5, compoundKey.getInnerCode());
            statement.setInt(6, compoundKey.getVendCode());
            statement.setInt(7,orderDesc.getCompoundKey().getSyntCode());
            statement.setInt(8,orderDesc.getCompoundKey().getInnerCode());
            statement.setInt(9,orderDesc.getCompoundKey().getVendCode());
            statement.executeUpdate();
            result=findEntityById(compoundKey);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<OrderDesc> findAllByOrderKey(Integer key)  {
        ArrayList<OrderDesc> list=new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_BY_ORDER);
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                list.add(generate(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return list;


    }
    private OrderDesc generate(ResultSet resultSet) throws SQLException {
        OrderDesc orderDesc = new OrderDesc();
        orderDesc.getCompoundKey().setSyntCode(resultSet.getInt(1));//SYNT_CODE
        orderDesc.getCompoundKey().setInnerCode(resultSet.getInt(2));//INNER_CODE
        orderDesc.getCompoundKey().setVendCode(resultSet.getInt(3));//VEND_CODE
        orderDesc.setSupplyDesc(resultSet.getString(4));//SUPPL_DESCRIPTION
        orderDesc.setPrice(resultSet.getBigDecimal(5));//PRICE
        orderDesc.setQty(resultSet.getInt(6));//QTY
        orderDesc.setAmount(resultSet.getBigDecimal(7));//AMOUNT
        orderDesc.setEntityStatus(Entity.EntityStatus.SELECT);
        return orderDesc;
    }

}
