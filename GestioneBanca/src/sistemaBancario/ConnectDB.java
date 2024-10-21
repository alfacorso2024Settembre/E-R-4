package sistemaBancario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/banca";
    private static final String USER = "root";
    private static final String PASSWORD = "corso";
    private Connection conn;

    public ConnectDB(){
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }
}
