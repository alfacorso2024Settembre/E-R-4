package it.alfasoft;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoAssicuratore {
    private static final String URL = "jdbc:mysql://localhost:3306/assicurazioni";
    private static final String USER = "root";
    private static final String PASSWORD = "Corso2024!";

    public static List<Object> read(int limit, int offset, String ricerca) throws SQLException {
        List<Object> result = new ArrayList<>();

        try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String query = "SELECT * FROM " + ricerca + " LIMIT ? OFFSET ?";
            try (PreparedStatement pst = con.prepareStatement(query)){
                pst.setInt(1,limit);
                pst.setInt(2,offset);

                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    if(ricerca.equalsIgnoreCase("beni")){
                        result.add(new DtoBene(rs.getString("tipo")));
                    }
                    else if (ricerca.equalsIgnoreCase("polizze")) {
                        result.add(new DtoPolizza(
                                rs.getInt("copertura"),
                                rs.getDate("fine_validita").toLocalDate(),
                                rs.getDate("inizio_validita").toLocalDate(),
                                rs.getInt("prezzo"),
                                rs.getBoolean("stato")
                                )
                        );
                    }

                }
            }

        }

        return result;
    }

    public static void aggiungiBene(DtoBene bene) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String insBook = "INSERT INTO beni (tipo) VALUES (?)";
            try(PreparedStatement psLibro = conn.prepareStatement(insBook, Statement.RETURN_GENERATED_KEYS)){
                psLibro.setString(1, bene.getTipo());
                psLibro.executeUpdate();

                conn.commit();
            }
            catch (SQLException e){
                conn.rollback();
                System.out.println("Errore durante l'inserimento del bene. Transazione annullata.");
            }
        } catch (SQLException e) {
            System.out.println("Connessione non stabiliata");
        }
    }

    public static void rimuoviBene(DtoBene bene) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int idBene = getIdBene(bene, conn);
            System.out.println(idBene);
            // rimuozione polizze con quel tipo di bene
            // TODO: trovare modo per mantenere lo storico anche se si cancella il bene
            String delPolizza = "DELETE FROM polizze WHERE id_bene=?";
            try(PreparedStatement psPolizza = conn.prepareStatement(delPolizza)){
                psPolizza.setInt(1, idBene);
                psPolizza.executeUpdate();

                String delBene = "DELETE FROM beni WHERE id_bene = ?";
                try(PreparedStatement psBene = conn.prepareStatement(delBene)){
                    psBene.setInt(1, idBene);
                    psBene.executeUpdate();

                    conn.commit();
                }
                catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                    System.out.println("Errore durante l'eliminazione del bene. Transazione annullata.");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
                System.out.println("Errore durante l'eliminazione della polizza associata al bene. Transazione annullata.");
            }

        } catch (SQLException e) {
            System.out.println("Connessione non stabiliata");
        }
    }

    public static int getIdBene(DtoBene bene, Connection conn)  {
        int idBene = 0;
        String selBene = "SELECT id_bene FROM beni WHERE tipo =?";

        try (PreparedStatement psEditore = conn.prepareStatement(selBene)) {
            psEditore.setString(1, bene.getTipo());

            ResultSet rsBene = psEditore.executeQuery();
            if (rsBene.next()){
                idBene = rsBene.getInt("id_bene");
            }
        }
        catch (SQLException e){
            System.out.println("Bene non previsto.");
        }

        return idBene;
    }
}
