package it.alfasoft.corso.esercizio;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DaoUtente {
    //creazione obiettivo
    /*-----------------------------------------------------------------------------------*/
    public int create(DtoObiettivo obiettivo){
        try (Connection conn = getConnection()){
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            String query="insert into obiettivi(descrizione,data_inizio,data_fine,progresso_attuale, progresso_totale,stato_completato,id_utente) values(?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,obiettivo.getDescrizione());
                ps.setDate(2, Date.valueOf(dateformatter.format(obiettivo.getData_inizio())));
                ps.setDate(3, Date.valueOf(dateformatter.format(obiettivo.getData_fine())));
                ps.setInt(4,obiettivo.getProgresso_attuale());
                ps.setInt(5,obiettivo.getProgresso_totale());
                ps.setBoolean(6,obiettivo.isStato_completato());
                int id_utente = getIdByUtente(obiettivo.getUtente());
                if(id_utente<0){//utente non esiste
                    id_utente = create(obiettivo.getUtente(), conn);
                }
                ps.setInt(7,id_utente);
                ps.executeUpdate();
                conn.commit();
            }catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
    /*-----------------------------------------------------------------------------------*/
    //Creazione utente
    /*-----------------------------------------------------------------------------------*/
    private int create(DtoUtente utente, Connection conn) throws SQLException{
        int id=-1;
        String query ="insert into utenti(nome_utente,cf) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,utente.getNome_utente());
        ps.setString(2,utente.getCf());
        ps.executeUpdate();
        ResultSet key =ps.getGeneratedKeys();
        key.next();
        id=key.getInt(1);
        return id;
    }

    /*-----------------------------------------------------------------------------------*/
    //Creazione attivita
    /*-----------------------------------------------------------------------------------*/
    public int create(DtoAttivitaFisica attivita){
        try(Connection conn = getConnection()){
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            String query="insert into attivita_fisiche(data_attivita,id_utente) values(?,?)";
            try(PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
                ps.setDate(1,Date.valueOf(dateformatter.format(attivita.getData_attivita())));
                int id_utente= getIdByUtente(attivita.getUtente());
                if(id_utente<0) {//autore non esiste;
                    id_utente = create(attivita.getUtente(), conn);
                }
                ps.setInt(2,id_utente);
                ps.executeUpdate();

                ResultSet key= ps.getGeneratedKeys();
                key.next();
                int id_attivita=key.getInt(1);
                for(DtoTipoAttivita tipo : attivita.getTipi_attivita()){
                    int id_tipo_attivita= getIdByTipoAttivita(tipo);
                    if(id_tipo_attivita<0){//autore non esiste;
                        id_tipo_attivita=create(tipo, conn);
                    }
                    associaTipo(id_attivita,id_tipo_attivita,conn);
                }
                System.out.println("id_attivita:"+id_attivita);
                advaceProgress(attivita, conn);

                conn.commit();
            }catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    /*-----------------------------------------------------------------------------------*/
    //Creazione tipo attivita
    /*-----------------------------------------------------------------------------------*/
    private int create(DtoTipoAttivita tipo, Connection conn) throws SQLException{
        int id=-1;
        String query ="insert into tipi_attivita(nome_tipo,descrizione) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,tipo.getNome_tipo());
        ps.setString(2, tipo.getDescrizione());
        ps.executeUpdate();
        ResultSet key =ps.getGeneratedKeys();
        key.next();
        id=key.getInt(1);
        return id;
    }
    /*-----------------------------------------------------------------------------------------------------------------*/
    //select * from obiettivi o join utenti u on o.id_utente = u.id_utente join attivita_fisiche af on u.id_utente=af.id_utente where af.data_attivita between o.data_inizio and o.data_fine and o.id_utente = ?;
    private void advaceProgress(DtoAttivitaFisica attivita, Connection conn) throws SQLException {

        String query="select o.id_obiettivo from obiettivi o join utenti u on o.id_utente = u.id_utente join attivita_fisiche af on u.id_utente=af.id_utente where af.data_attivita between o.data_inizio and o.data_fine and o.id_utente = ?";
        PreparedStatement ps = conn.prepareStatement(query);

        int id_utente=getIdByUtente(attivita.getUtente());
        ps.setInt(1,id_utente);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id_obiettivo= rs.getInt("id_obiettivo");
            query= "update obiettivi set progresso_attuale=progresso_attuale+1 where id_obiettivo=? and id_utente=?";
            PreparedStatement ps2 = conn.prepareStatement(query);
            ps2.setInt(1,id_obiettivo);
            ps2.setInt(2,id_utente);
            System.out.println(id_utente+" utente, idobb:"+id_obiettivo);
            ps2.executeUpdate();
        }
        isCompleted(conn);

    }
    private void isCompleted(Connection conn) throws SQLException{
        String query = "select id_obiettivo,progresso_attuale, progresso_totale from obiettivi";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int progresso_attuale = rs.getInt("progresso_attuale");
            int progresso_totale =rs.getInt("progresso_totale");
            int id_obiettivo = rs.getInt("id_obiettivo");
            boolean stato_completato = progresso_attuale>=progresso_totale;
            query = "update obiettivi set stato_completato=? where id_obiettivo=?";
            PreparedStatement ps2 = conn.prepareStatement(query);
            ps2.setBoolean(1,stato_completato);
            ps2.setInt(2,id_obiettivo);
            ps2.executeUpdate();
        }
    }
    public int getIdByUtente(DtoUtente utente){
        int id=-1;
        try(Connection conn = getConnection()){
            String query = "select id_utente from utenti where nome_utente=? and cf=?"; //si assume che esca un solo result
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,utente.getNome_utente());
            ps.setString(2,utente.getCf());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id=rs.getInt("id_utente");
                System.out.println("id:"+id);
            }
        }catch (SQLException e){
            e.printStackTrace();
            //id non valido
        }
        System.out.println("id utente:"+id);
        return id;
    }
    private int getIdByAttivita(DtoAttivitaFisica attivita){
        int id=-1;
        try(Connection conn = getConnection()){
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
            String query = "select id_attivita from attivita_fisiche where data_attivita=?, id_utente=?"; //si assume che esca un solo result
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1,Date.valueOf(dateformatter.format(attivita.getData_attivita())));
            ps.setInt(2,getIdByUtente(attivita.getUtente()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id=rs.getInt("id_attivita");
                System.out.println("id:"+id);
            }
        }catch (SQLException e){
            e.printStackTrace();
            //id non valido
        }
        return id;
    }
    private int getIdByTipoAttivita(DtoTipoAttivita tipo){
        int id=-1;
        try(Connection conn = getConnection()){
            String query = "select id_tipo_attivita from tipi_attivita where nome_tipo=? and descrizione=?"; //si assume che esca un solo result
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,tipo.getNome_tipo());
            ps.setString(2,tipo.getDescrizione());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id=rs.getInt("id_tipo_attivita");
                System.out.println("id:"+id);
            }

        }catch (SQLException e){
            e.printStackTrace();
            //id non valido
        }
        return id;
    }
    //eventualmente aggiungere la possibilità di scegliere se escono più campi per il get ID

    private void associaTipo(int id_attivita, int id_tipo_attivita, Connection conn) throws SQLException{
        String query = "insert into gestione_attivita(id_attivita,id_tipo_attivita) values(?,?)";
        PreparedStatement ps1 = conn.prepareStatement(query);
        ps1.setInt(1,id_attivita);
        ps1.setInt(2, id_tipo_attivita);
        ps1.executeUpdate();
    }

    /*stampa*/
    public List<DtoObiettivo> read(int limit, int offset,DtoUtente utente){
        ArrayList<DtoObiettivo> lista = new ArrayList<>();
        try(Connection conn= getConnection()){
            int id_utente = getIdByUtente(utente);
            String query = "select * from obiettivi where id_utente=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id_utente);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                lista.add(new DtoObiettivo(rs.getInt("id_obiettivo"),rs.getString("descrizione"),rs.getDate("data_inizio"),rs.getDate("data_fine"),rs.getInt("progresso_attuale"), rs.getInt("progresso_totale"),rs.getBoolean("stato_completato")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    public int delete(DtoObiettivo obiettivo){
        try(Connection conn = getConnection()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            String query = "delete from obiettivi where descrizione=? and data_inizio=? and data_fine = ? and id_utente=?";
            try(PreparedStatement ps = conn.prepareStatement(query)){
                ps.setString(1,obiettivo.getDescrizione());
                ps.setDate(2,Date.valueOf(df.format(obiettivo.getData_inizio())));
                ps.setDate(3,Date.valueOf(df.format(obiettivo.getData_fine())));
                ps.setInt(4,getIdByUtente(obiettivo.getUtente()));
                ps.executeUpdate();
                conn.commit();
            }catch (SQLException e){
                e.printStackTrace();
                conn.rollback();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    private int getIdByObiettivo(DtoObiettivo obiettivo){
        int id=-1;
        try(Connection conn = getConnection()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String query = "select id_obiettivo from obiettivi where descrizione=? and data_inizio=? and data_fine=? and progresso_attuale=? and progresso_totale=? and stato_completato=? and id_cliente=?"; //si assume che esca un solo result
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,obiettivo.getDescrizione());
            ps.setDate(2,Date.valueOf(df.format(obiettivo.getData_inizio())));
            ps.setDate(3,Date.valueOf(df.format(obiettivo.getData_fine())));
            ps.setInt(4,obiettivo.getProgresso_attuale());
            ps.setInt(5,obiettivo.getProgresso_totale());
            ps.setInt(6,getIdByUtente(obiettivo.getUtente()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id=rs.getInt("id_tipo_attivita");
                System.out.println("id:"+id);
            }

        }catch (SQLException e){
            e.printStackTrace();
            //id non valido
        }
        return id;
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(ConnectDB.getURL(),ConnectDB.getUSER(),ConnectDB.getPASSWORD());
    }
}
