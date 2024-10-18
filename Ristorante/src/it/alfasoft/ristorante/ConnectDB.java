package it.alfasoft.ristorante;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ristorante";
    private static final String USER = "root";
    private static final String PASSWORD = "corso";
    private static Connection conn;

    public static Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione stabilita");
            } catch (SQLException e) {
                System.out.println("Connessione fallita");
                e.printStackTrace();
            }
        }
        return conn;
    }
/*
    public static void closeConn() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Connessione chiusa");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

 */
}
