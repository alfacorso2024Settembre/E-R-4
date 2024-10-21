package it.alfasoft.corso.db1.esercizio2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GestioneClienti extends Gestione{
    public int delete(String tabella, int id) throws SQLException {
        String query = "delete from clienti where id_cliente=?";
        PreparedStatement ps= conn.prepareStatement(query);
        ps.setObject(1,id);
        return ps.executeUpdate();
    }

}
