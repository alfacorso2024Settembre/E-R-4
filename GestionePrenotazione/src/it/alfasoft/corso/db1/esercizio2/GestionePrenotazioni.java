package it.alfasoft.corso.db1.esercizio2;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionePrenotazioni extends Gestione{
    public int delete(String tabella, int id) throws SQLException {
        String query = "delete from prenotazioni where numero_prenotazione=?";
        PreparedStatement ps= conn.prepareStatement(query);
        ps.setObject(1,id);
        return ps.executeUpdate();
    }
}
