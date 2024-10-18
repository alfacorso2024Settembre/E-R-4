package it.alfasoft.corso.esercizio;

import java.util.Date;
import java.util.List;

public class DtoAttivitaFisica {
    private int id_attivita;
    private Date data_attivita;
    private DtoUtente utente;
    private List<DtoTipoAttivita> tipi_attivita;

    public DtoAttivitaFisica(Date data_attivita, DtoUtente utente, List<DtoTipoAttivita> tipi_attivita) {
        this.data_attivita = data_attivita;
        this.utente = utente;
        this.tipi_attivita = tipi_attivita;
    }

    public void setId_attivita(int id_attivita) {
        this.id_attivita = id_attivita;
    }

    public int getId_attivita() {
        return id_attivita;
    }

    public Date getData_attivita() {
        return data_attivita;
    }

    public List<DtoTipoAttivita> getTipi_attivita() {
        return tipi_attivita;
    }

    public DtoUtente getUtente() {
        return utente;
    }
}
