package it.alfasoft.ristorante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaoProprietario {
    public static void inserisciIngredienti(List<DtoIngrediente> ingredienti) throws SQLException {
        try (Connection conn  = ConnectDB.getConn()){
            for(DtoIngrediente ingr : ingredienti){
                String q = "SELECT id_ingrediente FROM ingredienti WHERE nome = ?";
                try ( PreparedStatement stmt = conn.prepareStatement(q)){
                    stmt.setString(1, ingr.getNome());
                    ResultSet rs = stmt.executeQuery();
                    if(!rs.next()){
                        String q2="INSERT INTO ingredienti(nome, scadenza) VALUES(?,?)";
                        try(PreparedStatement stmt2 = conn.prepareStatement(q2, Statement.RETURN_GENERATED_KEYS)){
                            stmt2.setString(1, ingr.getNome());
                            stmt2.setDate(2, Date.valueOf(ingr.getScadenza()));
                            stmt2.executeUpdate();
                            ResultSet generatedKeys = stmt2.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                long id_ingrediente = generatedKeys.getLong(1);
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }catch (SQLException e){
            System.out.println("connessione fallita");
        }
    }

    public static long inserisciPiatto(DtoPiatto piatto) throws SQLException {
            String query = "INSERT INTO piatti (id_piatto, nome, prezzo, descrizione, tempo_preparazione) VALUES (?, ?, ?, ?, ?)";
            long righe = 0;
            try (Connection conn = ConnectDB.getConn();//TODO:dividi try
                 PreparedStatement ps = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                ps.setInt(1, piatto.getId_piatto());
                ps.setString(2, piatto.getNome());
                ps.setInt(3, piatto.getPrezzo());
                ps.setString(4, piatto.getDescrizione());
                ps.setInt(5, piatto.getTempo_preparazione());
                righe = ps.executeUpdate();
                inserisciIngredienti(UIRistorante.creaListaIngr());
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return righe;
        }

        //vendere libri
        public static long rimuoviPiatto() throws SQLException {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Inserisci il nome del piatto da rimuovere: ");
            String nome = scanner.nextLine();

            String query = "DELETE FROM piatti WHERE nome = ?";
            long righe = 0;

            try (Connection conn = ConnectDB.getConn();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, nome);

                righe = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return righe;
        }
    }

