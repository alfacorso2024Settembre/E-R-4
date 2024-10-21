package it.alfasoft.corso.db1.esercizio2;

import java.sql.*;
import java.util.ArrayList;

public abstract class Gestione {
    private static final String URL = "jdbc:mysql://localhost:3306/gestioneprenotazioni";
    private static final String USER = "root";
    private static final String PASSWORD = "Patata123.";
    Connection conn;
    Statement stmt;
    public Gestione(){
        try{
            conn=DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
        }catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
        }
    }

    public int insert(String tabella, Object[] parametri) throws SQLException {
        String query = "insert into "+tabella+"(";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from "+tabella);
        ResultSetMetaData rsmd = rs.getMetaData();
        int size= rsmd.getColumnCount();
        for(int i=2; i<=size;i++){
            query+= rsmd.getColumnLabel(i);
            if(i!= rsmd.getColumnCount()){
                query+=",";
            }
        }
        query+=") values (";
        for(int i=2; i<=size;i++){
            query+= "?";
            if(i!= size){
                query+=",";
            }
        }
        query+=");";
        PreparedStatement ps = conn.prepareStatement(query);
        for(int j=1;j<size;j++){
            ps.setObject(j, parametri[j-1]);
        }
        System.out.println(query);
        return ps.executeUpdate();
    }
    public abstract int delete(String tabella, int id) throws  SQLException;
    public int update(String tabella,int id,String id_name,String colonna,String nuovo) throws SQLException{ //update tablename set columnname = value, ... where condition
        String query= "update "+tabella+" set "+colonna+" = ?" + " where "+id_name+"=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setObject(1, nuovo);
        ps.setObject(2, id);
        return ps.executeUpdate();
    }
    public void search(String tabella, Object elem) throws SQLException{
        String query = "select * from "+tabella+" where ";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from "+tabella);
        ResultSetMetaData rsmd = rs.getMetaData();
        int size= rsmd.getColumnCount();
        for(int i=2; i<=size;i++){
            query+= rsmd.getColumnLabel(i)+"=?";
            if(i!= rsmd.getColumnCount()){
                query+=" or ";
            }
        }
        System.out.println(query);
        PreparedStatement ps = conn.prepareStatement(query);
        for(int i=1;i<size;i++){
            ps.setObject(i,elem);
        }

        ArrayList<Object> row = new ArrayList<>();
        ResultSet result = ps.executeQuery();
        while (result.next()){
            ResultSetMetaData rsmd1= result.getMetaData();
            for(int i = 1; i<= rsmd1.getColumnCount();i++){
                row.add(result.getObject(i));
            }
            System.out.println(row);
            row.clear();
        }
    }
    public void stampa(String nome_tabella) throws  SQLException{
        Statement stmt = conn.createStatement();
        ArrayList<Object> row = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM "+nome_tabella+ " LIMIT 10");

        while (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1 ; i<= rsmd.getColumnCount();i++){
                row.add(rs.getObject(i));
            }
            System.out.println(row);
            row.clear();
        }
    }

}
