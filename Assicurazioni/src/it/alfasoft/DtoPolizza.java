package it.alfasoft;

import java.time.LocalDate;

public class DtoPolizza {
    private int id_polizza;
    private int perc_copertura;
    private int prezzo;
    private LocalDate inizio_validita;
    private LocalDate fine_validita;
    private boolean stato;

    public DtoPolizza(int perc_copertura, LocalDate fine_validita, LocalDate inizio_validita, int prezzo, boolean stato) {
        this.perc_copertura = perc_copertura;
        this.fine_validita = fine_validita;
        this.inizio_validita = inizio_validita;
        this.prezzo = prezzo;
        this.stato = stato;
    }

    public DtoPolizza(int id_polizza, int perc_copertura, LocalDate inizio_validita, LocalDate fine_validita, int prezzo, boolean stato) {
        this.id_polizza = id_polizza;
        this.perc_copertura = perc_copertura;
        this.prezzo = prezzo;
        this.inizio_validita = inizio_validita;
        this.fine_validita = fine_validita;
        this.stato = stato;
    }

    public DtoPolizza(int perc_copertura, int prezzo, LocalDate inizio_validita) {
        this.perc_copertura = perc_copertura;
        this.prezzo = prezzo;
        this.inizio_validita = inizio_validita;
    }

    public int getPerc_copertura() {
        return perc_copertura;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public LocalDate getInizio_validita() {
        return inizio_validita;
    }

    @Override
    public String toString() {
        return "Polizza{" +
                "id_polizza=" + id_polizza +
                ", perc_copertura=" + perc_copertura +
                ", prezzo=" + prezzo +
                ", inizio_validita=" + inizio_validita +
                ", fine_validita=" + fine_validita +
                ", stato=" + stato +
                '}';
    }
}
