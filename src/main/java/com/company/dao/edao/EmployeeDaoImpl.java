package com.company.dao.edao;

import com.company.dao.DBConnector;
import com.company.dao.idao.EmployeeDao;
import com.company.model.entity.Employee;
import com.company.model.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final String SQL_SELECT_ALL_EMPLOYEE = "SELECT E.STAFF_CODE, E.FULL_NAME, E.DEP_INDEX, D.DEP_NAME, D.SITY, D.REGION FROM EMPLOYEE E JOIN DEPARTMENTS D on D.DEP_INDEX = E.DEP_INDEX";
    private static final String SQL_DELETE_EMPLOYEE_BY_KEY = "DELETE FROM EMPLOYEE WHERE STAFF_CODE=?";
    private static final String SQL_INSERT_EMPLOYEE = "INSERT INTO EMPLOYEE VALUES(?,?)";
    private static final String SQL_SELECT_ALL_STAFF_CODES="SELECT STAFF_CODE FROM EMPLOYEE ORDER BY STAFF_CODE";

    @Override
    public List<Employee> findAll() throws SQLException {
        ArrayList<Employee> list=new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ALL_EMPLOYEE);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Employee employee=new Employee();
                employee.setStaffCode(resultSet.getInt(1));
                employee.setFullName(resultSet.getString(2));
                employee.setDepIndex(resultSet.getInt(3));
                employee.setDepName(resultSet.getString(4));
                employee.setCity(resultSet.getString(5));
                employee.setRegion(resultSet.getString(6));
                list.add(employee);
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
    public Employee findEntityById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Employee employee) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_DELETE_EMPLOYEE_BY_KEY);
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
    public boolean create(Employee employee) throws SQLException {
        boolean isDone=false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_INSERT_EMPLOYEE);
            statement.setString(1, employee.getFullName());
            statement.setInt(2,employee.getDepIndex());
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
    public Employee update(Employee employee) throws SQLException {
        return null;
    }

    @Override
    public List<Integer> getAllStaffCodes() {
        ArrayList<Integer> list=new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_STAFF_CODES);
            while(resultSet.next()){
                list.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return list;
    }
}
