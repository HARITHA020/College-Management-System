package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // 🔹 Static block → runs once when class loads
    static {
        try {
            Properties prop = new Properties();

            // ✅ Load db.properties from resources folder
            InputStream is = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (is == null) {
                System.out.println("db.properties file not found!");
            } else {
                prop.load(is);

                URL = prop.getProperty("db.url");
                USER = prop.getProperty("db.user");
                PASSWORD = prop.getProperty("db.password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Method to get connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}