package com.company.dao.edao;

import com.company.dao.DBConnector;
import com.company.dao.idao.SalesReceiptDao;
import com.company.model.SalesReceipt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesReceiptDaoImpl implements SalesReceiptDao {
    private static final String SQL_SELECT_ALL = "SELECT * FROM ORDERS";
    @Override
    public List<SalesReceipt> findAll() throws SQLException {
        List<SalesReceipt> list=new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection=DBConnector.getConnection();
            statement=connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while ()
        }
        return null;
    }

    @Override
    public SalesReceipt findEntityById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(SalesReceipt salesReceipt) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return false;
    }

    @Override
    public boolean create(SalesReceipt salesReceipt) throws SQLException {
        return false;
    }

    @Override
    public SalesReceipt update(SalesReceipt salesReceipt) throws SQLException {
        return null;
    }
}
