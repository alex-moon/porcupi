package com.github.alex_moon.porcupi;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;


public class Config {
    public static String dbType = "mysql";
    public static String dbHost = "127.0.0.1";
    public static String dbName = "porcupi";
    public static String dbUser = "porcupi";
    public static String dbPass = "porcupi";
    private static ConnectionSource db;
    
    public static ConnectionSource getDb() {
        if (db == null) {
            try {
                db = new JdbcConnectionSource(String.format(
                    "jdbc:%s://%s/%s?user=%s&password=%s",
                    dbType,
                    dbHost,
                    dbName,
                    dbUser,
                    dbPass
                ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return db;
    }
    
    public static Integer managerPort = 6969;
}
