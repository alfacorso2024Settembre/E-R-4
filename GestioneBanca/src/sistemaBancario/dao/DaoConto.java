package sistemaBancario.dao;

import sistemaBancario.ConnectDB;
import sistemaBancario.dto.DtoCliente;
import sistemaBancario.dto.DtoConto;

import java.sql.*;

public class DaoConto {

    public int apriConto(DtoConto elemento, String codice_utente) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        int numeroConto=0;
        try {
            conn.setAutoCommit(false);
            String queryConto = "INSERT INTO Conti (iban, saldo, banca) VALUES (?, ?, ?)";
            try(PreparedStatement stmtConto = conn.prepareStatement(queryConto, Statement.RETURN_GENERATED_KEYS)) {
                stmtConto.setString(1, elemento.getIban());
                stmtConto.setDouble(2, elemento.getSaldo());
                stmtConto.setString(3, elemento.getBanca());
                stmtConto.executeUpdate();
                ResultSet generatedKeys = stmtConto.getGeneratedKeys();
                if (generatedKeys.next()) {
                    numeroConto = generatedKeys.getInt(1);
                    String queryContiUtente = "INSERT INTO ContiUtente (numero_conto, id_cliente) VALUES (?, ?)";
                    try(PreparedStatement stmtContiUtente = conn.prepareStatement(queryContiUtente)) {
                        stmtContiUtente.setInt(1, numeroConto);
                        stmtContiUtente.setInt(2, getIdCliente(codice_utente));
                        stmtContiUtente.executeUpdate();
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            System.out.println("errore nella creazione del conto" + e.getMessage());
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
        return numeroConto;
    }


    public void chiudiConto(int numeroConto, String codiceUtente) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        try {
            conn.setAutoCommit(false);
            if (verificaAccesso(numeroConto,codiceUtente)) {
                String deleteCartaQuery = "DELETE FROM Carta WHERE numero_conto = ?";
                try (PreparedStatement deleteCartaStmt = conn.prepareStatement(deleteCartaQuery)) {
                    deleteCartaStmt.setInt(1, numeroConto);
                    deleteCartaStmt.executeUpdate();
                }

                String deleteContiUtenteQuery = "DELETE FROM ContiUtente WHERE numero_conto = ?";
                try (PreparedStatement deleteContiUtenteStmt = conn.prepareStatement(deleteContiUtenteQuery)) {
                    deleteContiUtenteStmt.setInt(1, numeroConto);
                    deleteContiUtenteStmt.executeUpdate();
                }

                String deleteContoQuery = "DELETE FROM Conti WHERE numero_conto = ?";
                try (PreparedStatement deleteContoStmt = conn.prepareStatement(deleteContoQuery)) {
                    deleteContoStmt.setInt(1, numeroConto);
                    deleteContoStmt.executeUpdate();
                }

                conn.commit();
                System.out.println("Conto chiuso con successo.");
            } else {
                System.out.println("Errore: il conto non è associato a questo utente.");
                conn.rollback();
            }
        }catch (SQLException e) {
            conn.rollback();
            System.out.println("Errore durante la chiusura del conto: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }



    public void vediSaldo(int numeroConto, String codiceUtente) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        if (verificaAccesso(numeroConto,codiceUtente)) {
            String query = "SELECT saldo FROM conti WHERE numero_conto = ?";
            try(PreparedStatement ps = conn.prepareStatement(query)){
                ps.setInt(1,numeroConto);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    System.out.println("Saldo: " + rs.getInt("saldo"));
                }
            }
        }
    }

    public void effettuaTransazione(String causale, double importo, Date dataTransazione, String codiceUtente, int numeroConto, String codiceDestinatario) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        try {
            conn.setAutoCommit(false);
            if (verificaAccesso(numeroConto, codiceUtente)) {

                String saldoQuery = "SELECT saldo FROM Conti WHERE numero_conto = ?";
                double saldoAttuale = 0;
                try (PreparedStatement ps = conn.prepareStatement(saldoQuery)) {
                    ps.setInt(1, numeroConto);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        saldoAttuale = rs.getDouble("saldo");
                    }
                }

                if (saldoAttuale < importo) {
                    System.out.println("Errore: saldo insufficiente.");
                    return;
                }

                int numeroContoDestinatario = getNumeroConto(codiceDestinatario);
                if (numeroContoDestinatario == -1) {
                    System.out.println("Errore: destinatario non trovato.");
                    return;
                }

                String updateSaldoQuery = "UPDATE Conti SET saldo = saldo - ? WHERE numero_conto = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSaldoQuery)) {
                    ps.setDouble(1, importo);
                    ps.setInt(2, numeroConto);
                    ps.executeUpdate();
                }

                String updateSaldoDestQuery = "UPDATE Conti SET saldo = saldo + ? WHERE numero_conto = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSaldoDestQuery)) {
                    ps.setDouble(1, importo);
                    ps.setInt(2, numeroContoDestinatario);
                    ps.executeUpdate();
                }

                String transazioneQuery = "INSERT INTO Transazioni (causale, importo, data_transazione, codice_cliente, codice_destinatario) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(transazioneQuery)) {
                    ps.setString(1, causale);
                    ps.setDouble(2, importo);
                    ps.setDate(3, dataTransazione);
                    ps.setString(4, codiceUtente);
                    ps.setString(5, codiceDestinatario);
                    ps.executeUpdate();
                }

                conn.commit();
                System.out.println("Transazione completata con successo.");
            } else {
                System.out.println("Accesso non consentito al conto.");
                conn.rollback();
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Errore durante la transazione: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    private int getNumeroConto(String codiceDestinatario) throws SQLException {
        int numeroConto = -1;
        String query = "SELECT numero_conto FROM Clienti INNER JOIN ContiUtente ON Clienti.id_cliente = ContiUtente.id_cliente WHERE codice_cliente = ?";
        try (Connection conn = new ConnectDB().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codiceDestinatario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numeroConto = rs.getInt("numero_conto");
                }
            }
        }
        return numeroConto;
    }

    public void add(DtoCliente elemento) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        String query= "INSERT INTO Clienti (codice_cliente,pin) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setObject(1, elemento.getCodice_utente());
        ps.setObject(2, elemento.getPin());
        ps.executeUpdate();
    }
    //verifica che l'utente esista
    public boolean login(String codice_utente, String pin) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        boolean exist = false;
        String query = "SELECT * FROM Clienti WHERE codice_cliente = '" + codice_utente + "' AND pin = '" + pin + "'";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            exist=true;
        }
        return exist;
    }
    //genere codice cliente progressivo
    public String generaCU() throws SQLException {
        Connection conn = new ConnectDB().getConn();
        String codiceCliente = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            String query = "SELECT codice_cliente FROM Clienti ORDER BY codice_cliente DESC LIMIT 1";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                codiceCliente = rs.getString("codice_cliente");
                if (codiceCliente != null) {
                    String cod = codiceCliente.substring(0, 1);
                    int num = Integer.parseInt(codiceCliente.substring(1)) + 1;
                    codiceCliente = cod + num;
                }
            } else {
                codiceCliente = "C1";
            }
        } finally {
            conn.close();
        }

        return codiceCliente;
    }

    public int getIdCliente(String codice_utente) throws SQLException {
        String query = "SELECT id_cliente FROM Clienti WHERE codice_cliente = ?";
        try (Connection conn = new ConnectDB().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codice_utente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
            }
        }
        return -1; // Se non trovato
    }

    public boolean verificaAccesso(int numeroConto, String codiceUtente) throws SQLException {
        Connection conn = new ConnectDB().getConn();
        boolean consentito = false;
        String verificaQuery = "SELECT COUNT(*) FROM ContiUtente WHERE numero_conto = ? AND id_cliente = ?";
        try (PreparedStatement verificaStmt = conn.prepareStatement(verificaQuery)) {
            verificaStmt.setInt(1, numeroConto);
            verificaStmt.setInt(2, getIdCliente(codiceUtente));
            try (ResultSet rs = verificaStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    consentito = true;
                }else{
                    System.out.println("ACCESSO NON CONSENTITO");
                }
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Errore durante la verifica: " + e.getMessage());
        } finally {
            conn.close();
        }
        return consentito;
    }
}
