package com.company.dao;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static final String dbPropertiesPath="database.properties";
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    static {
        try {
            properties.load(new FileReader(DBConnector.class.getResource(dbPropertiesPath).getFile()));
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        } catch (IOException |SQLException e) {
            e.printStackTrace(); // fatal exception
        }
        DATABASE_URL = (String) properties.get("db.url");
    }

    private DBConnector() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }



}
