package it.alfasoft.ristorante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class DaoCliente {
    //effettua ordinazione ()
    public static List<DtoPiatto> listaPiatti() {
        System.out.println("Che piatti desideri ordinare?");
        final String TABLE = "piatti";
        List<DtoPiatto> listaP = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE + ";";

        try (PreparedStatement ps = ConnectDB.getConn().prepareStatement(select);
             ResultSet rs = ps.executeQuery()) {

            // Popola la lista con i piatti disponibili
            while (rs.next()) {
                listaP.add(new DtoPiatto(
                        rs.getInt("id_piatto"),
                        rs.getString("nome"),
                        rs.getInt("prezzo"),
                        rs.getString("descrizione"),
                        rs.getInt("tempo_preparazione")
                ));
            }

            // Stampa i piatti disponibili
            for (DtoPiatto piatto : listaP) {
                System.out.println(piatto);
            }

            Scanner scanner = new Scanner(System.in);
            boolean programma = true;

            while (programma) {
                System.out.println("Inserisci l'ID del piatto da ordinare (o -1 per terminare):");
                int nrPiatto = scanner.nextInt();

                // Condizione di uscita
                if (nrPiatto == -1) {
                    programma = false;
                    continue;
                }

                // Verifica se l'ID del piatto esiste
                boolean found = false;
                for (DtoPiatto piatto : listaP) {
                    if (nrPiatto == piatto.getId_piatto()) {
                        found = true;
                        System.out.println("Hai selezionato: " + piatto);
                        // Qui potresti voler aggiungere logica per memorizzare il piatto ordinato
                        break;
                    }
                }

                if (!found) {
                    System.out.println("ID piatto non valido. Riprova.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaP;
    }

    public static long effettuaOrdine(int id_cliente, List<DtoPiatto> piatti) throws SQLException {
        String query = "INSERT INTO ordini (stato, id_cliente, tavolo) VALUES (?, ?, ?)";
        long idOrdine = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il numero del tavolo: ");
        int tavolo = scanner.nextInt();

        try (Connection conn = ConnectDB.getConn()) {

            conn.setAutoCommit(false);

            try(PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, String.valueOf(Stati.IN_PREPARAZIONE));
                ps.setInt(2, id_cliente);
                ps.setInt(3, tavolo);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idOrdine = generatedKeys.getLong(1);
                    }


                    String query3 = "INSERT INTO ordini_piatti (id_piatto, id_ordine) VALUES (?, ?)";
                    for (DtoPiatto piatto : piatti) {
                        try (PreparedStatement ps3 = conn.prepareStatement(query3)) {
                            ps3.setInt(1, piatto.getId_piatto());
                            ps3.setLong(2, idOrdine);
                            ps3.executeUpdate();
                        }
                    }

                    conn.commit(); // Commetti le modifiche
                } else {
                    throw new SQLException("Nessun ordine creato.");
                }
            } catch(SQLException e){
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return idOrdine;
    }

    //vedi piatti
    public static List<DtoPiatto> vediMenu(int limit, int offset) throws SQLException {
        final String TABLE = "piatti";
        List<DtoPiatto> lista = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE + " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = ConnectDB.getConn().prepareStatement(select)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DtoPiatto(
                            rs.getInt("id_piatto"),
                            rs.getString("nome"),
                            rs.getInt("prezzo"),
                            rs.getString("descrizione"),
                            rs.getInt("tempo_preparazione")
                    ));
                }
                for (DtoPiatto piatto : lista) {
                    System.out.println(piatto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //vedi piatti tollerabili
    public static List<DtoPiatto> vediPiattiTollerabili(int limit, int offset) throws SQLException {
        String intolleranza = UIRistorante.chiediIntolleranze();
        final String TABLE = "piatti";
        List<DtoPiatto> lista = new ArrayList<>();
        String query = "SELECT piatti.*, GROUP_CONCAT(intolleranze.tipo SEPARATOR ', ') AS intolleranze " +
                "FROM piatti " +
                "JOIN piatti_ingredienti ON piatti.id_piatto = piatti_ingredienti.id_piatto " +
                "JOIN ingredienti ON piatti_ingredienti.id_ingrediente = ingredienti.id_ingrediente " +
                "LEFT JOIN ingredienti_intolleranze ON ingredienti.id_ingrediente = ingredienti_intolleranze.id_ingrediente " +
                "LEFT JOIN intolleranze ON ingredienti_intolleranze.tipo = intolleranze.tipo " +
                "WHERE piatti.id_piatto NOT IN ( " +
                "    SELECT piatti.id_piatto " +
                "    FROM piatti " +
                "    JOIN piatti_ingredienti ON piatti.id_piatto = piatti_ingredienti.id_piatto " +
                "    JOIN ingredienti ON piatti_ingredienti.id_ingrediente = ingredienti.id_ingrediente " +
                "    JOIN ingredienti_intolleranze ON ingredienti.id_ingrediente = ingredienti_intolleranze.id_ingrediente " +
                "    WHERE ingredienti_intolleranze.tipo = ? " +
                ") " +
                "GROUP BY piatti.id_piatto " +
                "LIMIT ? OFFSET ?";


        try (PreparedStatement ps = ConnectDB.getConn().prepareStatement(query)) {
            ps.setString(1, intolleranza); // Imposta il parametro per l'intolleranza
            ps.setInt(2, limit); // Imposta il parametro per il limite
            ps.setInt(3, offset); // Imposta il parametro per l'offset
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DtoPiatto(
                            rs.getInt("id_piatto"),
                            rs.getString("nome"),
                            rs.getInt("prezzo"),
                            rs.getString("descrizione"),
                            rs.getInt("tempo_preparazione")
                    ));
                }
                for (DtoPiatto piatto : lista) {
                    System.out.println(piatto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
