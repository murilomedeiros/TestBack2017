/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testBack.dataBase;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author muril
 */
public class Connection {
    
    /**
     * @param args the command line arguments
     */
    private static String DB_DRIVER = "org.postgresql.Driver";
    private static String DB_CONNECTION = "jdbc:postgresql://localhost:5432/postgres";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pwd";

    public static String getDB_DRIVER() {
        return DB_DRIVER;
    }

    public static void setDB_DRIVER(String aDB_DRIVER) {
        DB_DRIVER = aDB_DRIVER;
    }

    public static String getDB_CONNECTION() {
        return DB_CONNECTION;
    }

    public static void setDB_CONNECTION(String aDB_CONNECTION) {
        DB_CONNECTION = aDB_CONNECTION;
    }

    public static String getDB_USER() {
        return DB_USER;
    }

    public static void setDB_USER(String aDB_USER) {
        DB_USER = aDB_USER;
    }

    public static String getDB_PASSWORD() {
        return DB_PASSWORD;
    }

    public static void setDB_PASSWORD(String aDB_PASSWORD) {
        DB_PASSWORD = aDB_PASSWORD;
    }
    
    
    public static java.sql.Connection getDBConnection() {

        java.sql.Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }
}
