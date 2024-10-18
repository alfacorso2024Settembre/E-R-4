package it.alfasoft.corso.esercizio;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DtoObiettivo {
    private int id_obiettivo;
    private String descrizione;
    private Date data_inizio;
    private Date data_fine;
    private int progresso_attuale;
    private int progresso_totale;
    private boolean stato_completato;
    private DtoUtente utente;

    public DtoObiettivo(int id_obiettivo,String descrizione, Date data_inizio, Date data_fine, int progresso_attuale, int progresso_totale,boolean stato_completato) {
        this.id_obiettivo=id_obiettivo;
        this.descrizione = descrizione;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.stato_completato = stato_completato;
        this.progresso_attuale = progresso_attuale;
        this.progresso_totale = progresso_totale;
    }

    public DtoObiettivo(String descrizione, Date data_inizio, Date data_fine, DtoUtente utente) {
        this.descrizione = descrizione;
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.progresso_attuale = 0;
        this.progresso_totale = (int) TimeUnit.DAYS.convert(data_fine.getTime() - data_inizio.getTime(), TimeUnit.MILLISECONDS);
        this.utente = utente;
        this.stato_completato=false;
    }

    public void setId_obiettivo(int id_obiettivo) {
        this.id_obiettivo = id_obiettivo;
    }

    public void setProgresso_attuale(int progresso_attuale) {
        this.progresso_attuale = progresso_attuale;
    }

    public int getId_obiettivo() {
        return id_obiettivo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Date getData_inizio() {
        return data_inizio;
    }

    public Date getData_fine() {
        return data_fine;
    }

    public int getProgresso_attuale() {
        return progresso_attuale;
    }

    public int getProgresso_totale() {
        return progresso_totale;
    }

    public DtoUtente getUtente() {
        return utente;
    }

    public boolean isStato_completato() {
        return stato_completato;
    }

    @Override
    public String toString() {
        return "DtoObiettivo{" +
                "descrizione='" + descrizione + '\'' +
                ", data_inizio=" + data_inizio +
                ", data_fine=" + data_fine +
                ", progresso_attuale=" + progresso_attuale +
                ", progresso_totale=" + progresso_totale +
                ", stato_completato=" + stato_completato +
                '}';
    }
}
