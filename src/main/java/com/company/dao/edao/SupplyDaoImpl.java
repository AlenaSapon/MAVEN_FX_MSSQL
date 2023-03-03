package com.company.dao.edao;

import com.company.dao.DBConnector;
import com.company.dao.idao.SupplyDao;
import com.company.model.entity.Supply;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplyDaoImpl implements SupplyDao {

    private static final String SQL_SELECT_ALL_INNER_CODES_WITH_DESC = "SELECT INNER_CODE, SUPPL_NAME FROM SUPPLIES order by INNER_CODE";


    @Override
    public List<Supply> findAll() throws SQLException {
        return null;
    }

    @Override
    public Supply findEntityById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Supply supply) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return false;
    }

    @Override
    public boolean create(Supply supply) throws SQLException {
        return false;
    }

    @Override
    public Supply update(Supply supply) throws SQLException {
        return null;
    }

    @Override
    public List<Supply.InnerCodeDesc> getInnerCodesWithDesc() {
        ArrayList<Supply.InnerCodeDesc> list=new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_INNER_CODES_WITH_DESC);
            while(resultSet.next()){
                list.add(new Supply.InnerCodeDesc(resultSet.getInt(1), resultSet.getString(2)));
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
