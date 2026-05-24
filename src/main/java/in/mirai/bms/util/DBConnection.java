package in.mirai.bms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/bms";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "6524";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println("Connection Failed Check in DBConnection Class!!");
        }

        return null;
    }
}