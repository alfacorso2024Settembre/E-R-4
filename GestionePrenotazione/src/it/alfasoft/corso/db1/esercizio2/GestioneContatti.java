package it.alfasoft.corso.db1.esercizio2;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestioneContatti extends Gestione {
    public int delete(String tabella, int id) throws SQLException {
        String query = "delete from contatti where id_contatto=?";
        PreparedStatement ps= conn.prepareStatement(query);
        ps.setObject(1,id);
        return ps.executeUpdate();
    }
}
