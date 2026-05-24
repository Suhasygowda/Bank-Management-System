package in.mirai.bms.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Utility class to manage database connections.
 * This class provides a centralized way to obtain a connection to the MySQL database.
 */
public class DBConnection {

    /** Database connection URL */
    private static final String URL = "jdbc:mysql://localhost:3306/bms";

    /** Database username */
    private static final String USERNAME = "root";

    /** Database password */
    private static final String PASSWORD = "6524";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return A {@link Connection} object if successful, {@code null} otherwise.
     */
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Return connection object
            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
        } catch (Exception e) {
            System.err.println("Connection Failed: Check credentials or server status in DBConnection Class.");
            e.printStackTrace();
        }
        return null;
    }
}
