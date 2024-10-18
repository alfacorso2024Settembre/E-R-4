package it.alfasoft.corso.esercizio;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/fitness";
    private static final String USER = "root";
    private static final String PASSWORD = "Patata123.";

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }
}
