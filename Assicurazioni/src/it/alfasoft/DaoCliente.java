package it.alfasoft;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

public class DaoCliente {
    private static final String URL = "jdbc:mysql://localhost:3306/assicurazioni";
    private static final String USER = "root";
    private static final String PASSWORD = "Corso2024!";

    public static List<Object> visualizzaPolizza(int limit, int offset, DtoCliente cliente) throws SQLException {
        List<Object> result = new ArrayList<>();

        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int idCliente = getIdCliente(cliente, con);

            String query = "SELECT * FROM polizze WHERE id_cliente=? LIMIT ? OFFSET ?";
            try (PreparedStatement pst = con.prepareStatement(query)){
                pst.setInt(1,idCliente);
                pst.setInt(2,limit);
                pst.setInt(3,offset);

                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    result.add(new DtoPolizza(
                                    rs.getInt("id_polizza"),
                                    rs.getInt("copertura"),
                                    rs.getDate("fine_validita").toLocalDate(),
                                    rs.getDate("inizio_validita").toLocalDate(),
                                    rs.getInt("prezzo"),
                                    rs.getBoolean("stato")
                            )
                    );
                }
            }

            return result;
        }
    }

    private static int getIdCliente(DtoCliente cliente, Connection con) throws SQLException {
        int idCliente = 0;

        String selClient = "SELECT id_cliente FROM clienti WHERE codice_fiscale=? ";
        try (PreparedStatement psLibro = con.prepareStatement(selClient)) {
            psLibro.setString(1, cliente.getCodice_fiscale());

            ResultSet rs = psLibro.executeQuery();
            if(rs.next()){
                idCliente = rs.getInt("id_cliente");
            }
            else{
                idCliente = inserisciCliente(cliente, con);
            }

        }

        return idCliente;
    }

    private static int inserisciCliente(DtoCliente cliente, Connection con) throws SQLException {
        int clienteId = 0;

        String insClient = "INSERT INTO clienti (codice_fiscale, data_nascita, nome, cognome) VALUES (?,?,?,?)";
        try (PreparedStatement psInsert = con.prepareStatement(insClient, Statement.RETURN_GENERATED_KEYS)) {
            psInsert.setString(1, cliente.getCodice_fiscale());
            psInsert.setDate(2, Date.valueOf(cliente.getData_nascita()));
            psInsert.setString(3, cliente.getNome());
            psInsert.setString(4, cliente.getCognome());

            psInsert.executeUpdate();
            ResultSet resId = psInsert.getGeneratedKeys();
            if (resId.next()){
                clienteId = resId.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
        }

        return clienteId;
    }

    public static void assicuraBene(DtoBene bene, DtoCliente cliente, DtoPolizza polizza) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            // cerca bene: se non c'è errore
            int beneId = DaoAssicuratore.getIdBene(bene, conn);
            // se c'è, cerca id cliente
            int clientId = getIdCliente(cliente, conn);
            // recupero id importo in base alla copertura
            int risarcimentoId = getIdRisarcimento(polizza.getPerc_copertura(), conn);

            // inserisci polizza
            String insBook = "INSERT INTO polizze (copertura, prezzo, inizio_validita, fine_validita, stato, id_cliente, id_risarcimento, id_bene) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
            try(PreparedStatement psLibro = conn.prepareStatement(insBook)){
                psLibro.setInt(1, polizza.getPerc_copertura());
                psLibro.setInt(2, polizza.getPrezzo());
                psLibro.setDate(3, Date.valueOf(polizza.getInizio_validita()));
                psLibro.setDate(4, Date.valueOf(polizza.getInizio_validita().plusYears(1)));
                psLibro.setBoolean(5, polizza.getInizio_validita().isEqual(now()));
                psLibro.setInt(6, clientId);
                psLibro.setInt(7, risarcimentoId);
                psLibro.setInt(8, beneId);
                psLibro.executeUpdate();

                conn.commit();
            }
            catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
                System.out.println("Errore durante l'inserimento della polizza. Transazione annullata.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connessione non stabiliata");
        }
    }



    private static int getIdRisarcimento(int copertura, Connection conn) throws SQLException{
        int idRisarcimento = 0;
        int importo = 0;

        if (copertura==5){
            importo = 3000;
        }
        else if (copertura==10) {
            importo = 5000;
        }
        else if (copertura == 30) {
            importo = 10000;
        }
        else{
            importo = 15000;
        }

        String selClient = "SELECT id_risarcimento FROM risarcimenti WHERE importo=? ";
        try (PreparedStatement psLibro = conn.prepareStatement(selClient)) {
            psLibro.setInt(1, importo);

            ResultSet rs = psLibro.executeQuery();
            if(rs.next()){
                idRisarcimento = rs.getInt("id_risarcimento");
            }

        }

        return idRisarcimento;
    }


    public static void rinnovoPolizza(int idPolizza, DtoPolizza newPolizza) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            assicuraBene(getBene(idPolizza,conn), getCliente(idPolizza,conn), newPolizza);
/*
            int[] infoPolizzaVecchia = getInfoPolizza(idPolizza, conn);
            int beneId = infoPolizzaVecchia[0];
            int clientId = infoPolizzaVecchia[1];
            int risarcimentoId = getIdRisarcimento(polizza.getPerc_copertura(), conn);

            // inserisci polizza
            String insBook = "INSERT INTO polizze (copertura, prezzo, inizio_validita, fine_validita, stato, id_cliente, id_risarcimento, id_bene) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
            try(PreparedStatement psLibro = conn.prepareStatement(insBook)){
                psLibro.setInt(1, polizza.getPerc_copertura());
                psLibro.setInt(2, polizza.getPrezzo());
                psLibro.setDate(3, Date.valueOf(polizza.getInizio_validita()));
                psLibro.setDate(4, Date.valueOf(polizza.getInizio_validita().plusYears(1)));
                psLibro.setBoolean(5, polizza.getInizio_validita().isEqual(now()));
                psLibro.setInt(6, clientId);
                psLibro.setInt(7, risarcimentoId);
                psLibro.setInt(8, beneId);
                psLibro.executeUpdate();

                conn.commit();
            }
            catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
                System.out.println("Errore durante l'inserimento della polizza. Transazione annullata.");
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connessione non stabiliata");
        }
    }

    private static DtoCliente getCliente(int idPolizza, Connection conn) throws SQLException{
        DtoCliente cliente = null;

        String selClient = "SELECT c.codice_fiscale FROM clienti as c join polizze as p on c.id_cliente = p.id_cliente WHERE p.id_polizza=? ";
        try (PreparedStatement psLibro = conn.prepareStatement(selClient)) {
            psLibro.setInt(1, idPolizza);

            ResultSet rs = psLibro.executeQuery();
            if(rs.next()){
                cliente = new DtoCliente(rs.getString("codice_fiscale"));
            }
        }

        return cliente;
    }

    private static DtoBene getBene(int idPolizza, Connection conn) throws SQLException{
        DtoBene bene = null;

        String selClient = "SELECT tipo FROM beni as b join polizze as p on b.id_bene = p.id_bene WHERE p.id_polizza=? ";
        try (PreparedStatement psLibro = conn.prepareStatement(selClient)) {
            psLibro.setInt(1, idPolizza);

            ResultSet rs = psLibro.executeQuery();
            if(rs.next()){
                bene = new DtoBene(rs.getString("tipo"));
            }
        }

        return bene;
    }

    private static int[] getInfoPolizza(int idPolizza, Connection conn) throws SQLException {
        int[] info = new int[2];

        String selClient = "SELECT id_cliente, id_bene FROM polizze WHERE id_polizza=? ";
        try (PreparedStatement psLibro = conn.prepareStatement(selClient)) {
            psLibro.setInt(1, idPolizza);

            ResultSet rs = psLibro.executeQuery();
            if (rs.next()){
                info[0] = rs.getInt("id_bene");
                info[1] = rs.getInt("id_cliente");
            }
        }

        return info;
    }

}
