package com.company.dao.idao;

import com.company.model.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends Entity> {

    List<T> findAll() throws SQLException;

    T findEntityById(K id) throws SQLException;

    boolean delete(T t) throws SQLException;
    boolean delete(K id) throws SQLException;
    boolean create(T t) throws SQLException;
    T update(T t) throws SQLException;
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
// log
        }
    }
    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close(); // or connection return code to the pool
            }
        } catch (SQLException e) {
// log
        }
    }
    static void closeStatic(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
// log
        }
    }
    static void closeStatic(Connection connection) {
        try {
            if (connection != null) {
                connection.close(); // or connection return code to the pool
            }
        } catch (SQLException e) {
// log
        }
    }
}



