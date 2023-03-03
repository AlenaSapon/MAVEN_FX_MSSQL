package com.company.dao;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



import static com.company.dao.idao.BaseDao.closeStatic;

public class TableCreator {

    private static final String SQL_CREATE_AND_POPULATE_TABLES="SQLQuery_V6.sql";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(TableCreator.class.getResource(SQL_CREATE_AND_POPULATE_TABLES).getFile()));
        StringBuilder stringBuilder=new StringBuilder();
        String line=in.readLine();

        while (line!=null) {
            stringBuilder.append(line);
            line=in.readLine();
        }
        String[] queryArray = stringBuilder.toString().split(";");
        Connection connection=null;
        Statement statement = null;
        try {
            connection= DBConnector.getConnection();
            statement=connection.createStatement();
            for (String s : queryArray) {
                System.out.println(s.concat(", DML row count:").concat(String.valueOf(statement.executeUpdate(s))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatic(statement);
            closeStatic(connection);
        }

    }

}
