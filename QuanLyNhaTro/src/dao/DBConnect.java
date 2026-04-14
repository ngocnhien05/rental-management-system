package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    private static String url = "jdbc:mysql://localhost:3306/nhatro_db";
    private static String user = "your_username";
    private static String pass = "your_password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}