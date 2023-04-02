package com.company.dao.edao;

import com.company.dao.DBConnector;
import com.company.dao.idao.OrderDao;
import com.company.model.entity.Entity;
import com.company.model.entity.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT SYNT_CODE,ORDER_NUM,SUPPLIER,ORDER_DATE,STAFF_CODE,isnull(AMOUNT,0.00) FROM ORDERS";
    private static final String SQL_SELECT_ORDER_BY_SYNT_CODE = "SELECT SYNT_CODE,ORDER_NUM,SUPPLIER,ORDER_DATE,STAFF_CODE,isnull(AMOUNT,0.00) FROM ORDERS WHERE SYNT_CODE=?";
    private static final String SQL_DELETE_ORDER_BY_SYNT_CODE = "DELETE FROM ORDERS WHERE SYNT_CODE=?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO ORDERS VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE_ORDER ="UPDATE ORDERS SET ORDER_NUM=?, SUPPLIER=?, ORDER_DATE=?, STAFF_CODE=?, AMOUNT=? WHERE SYNT_CODE=?";
    private static final String SQL_CALCULATE_ORDER_AMOUNT ="{call dbo.CalculateOrderAmount(?, ?)}";
     /*
    create procedure CalculateOrderAmount
    @synt_code int,
    @rc decimal(10,2) =null OUT
    as
    begin
    set @rc= isnull((select sum(isnull(amount, 0.00)) from ORDERS_DESC where SYNT_CODE=@synt_code),0.00)
    return @rc
    end
    */



    private static final String SQL_DELETE_ORDERS_DESC_FOR_ORDER="DELETE FROM ORDERS_DESC WHERE SYNT_CODE=?";



    @Override
    public List<Order> findAll() throws SQLException {
        ArrayList<Order> list=new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ALL_ORDERS);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Order order = new Order();
                order.setSyntCode(resultSet.getInt(1));//SYNT_CODE
                order.setOrderNum(resultSet.getInt(2));//ORDER_NUM
                order.setSupplier(resultSet.getString(3));//SUPPLIER
                order.setDate(resultSet.getDate(4));//ORDER_DATE
                order.setStaffCode(resultSet.getInt(5));//STAFF_CODE
                order.setAmount(resultSet.getBigDecimal(6));//AMOUNT
                order.setEntityStatus(Entity.EntityStatus.SELECT);
                list.add(order);
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
    public Order findEntityById(Integer syntCode) throws SQLException {
        Order order=null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ORDER_BY_SYNT_CODE);
            statement.setInt(1,syntCode);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                order=new Order();
                order.setSyntCode(resultSet.getInt(1));//SYNT_CODE ---EDITED comment
                order.setOrderNum(resultSet.getInt(2));//ORDER_NUM
                order.setSupplier(resultSet.getString(3));//SUPPLIER
                order.setDate(resultSet.getDate(4));//ORDER_DATE
                order.setStaffCode(resultSet.getInt(5));//STAFF_CODE
                order.setAmount(resultSet.getBigDecimal(6));//AMOUNT
                order.setEntityStatus(Entity.EntityStatus.SELECT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return order;
    }

    @Override
    public boolean delete(Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_DELETE_ORDER_BY_SYNT_CODE);
            statement.setInt(1,id);
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
    public boolean create(Order order) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_INSERT_ORDER);
            statement.setInt(1,order.getOrderNum());
            statement.setString(2, order.getSupplier());
            statement.setDate(3, order.getDate());
            statement.setInt(4,order.getStaffCode());
            statement.setBigDecimal(5,order.getAmount());
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
    public Order update(Order order) throws SQLException {
        Order result=null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setInt(1,order.getOrderNum());
            statement.setString(2, order.getSupplier());
            statement.setDate(3, order.getDate());
            statement.setInt(4,order.getStaffCode());
            statement.setBigDecimal(5, order.getAmount());
            statement.setInt(6, order.getSyntCode());
            statement.executeUpdate();
            result=findEntityById(order.getSyntCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }


    @Override
    public BigDecimal calculateAmount(Integer syntCode) {
        BigDecimal amount=null;
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement = connection.prepareCall(SQL_CALCULATE_ORDER_AMOUNT);
            statement.setInt(1,syntCode);
            statement.registerOutParameter(2, Types.DECIMAL);
            statement.execute();
            amount=statement.getBigDecimal(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return amount;
    }

    @Override
    public boolean deleteWithDescription(Integer id) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        Savepoint savepoint=null;

        try {
            connection=DBConnector.getConnection();
            connection.setAutoCommit(false);
            statement=connection.prepareStatement(SQL_DELETE_ORDERS_DESC_FOR_ORDER);
            statement.setInt(1,id);
            statement.executeUpdate();
            savepoint=connection.setSavepoint();
            connection.commit();
            statement=connection.prepareStatement(SQL_DELETE_ORDER_BY_SYNT_CODE);
            statement.setInt(1,id);
            statement.executeUpdate();
            connection.commit();
            isDone=true;
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(savepoint);
            isDone=false;
        } finally {
            connection.setAutoCommit(true);
            close(statement);
            close(connection);

        }
        return isDone;
    }
}
