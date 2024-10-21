package it.alfasoft.corso.db1.esercizio2;

import java.time.LocalDateTime;

public class Prenotazione {
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine;
    private LocalDateTime data_prenotazione;
    private boolean stato;

    public Prenotazione(LocalDateTime data_inizio, LocalDateTime data_fine, LocalDateTime data_prenotazione, boolean stato) {
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.data_prenotazione = data_prenotazione;
        this.stato = stato;
    }

    public LocalDateTime getData_inizio() {
        return data_inizio;
    }

    public LocalDateTime getData_fine() {
        return data_fine;
    }

    public LocalDateTime getData_prenotazione() {
        return data_prenotazione;
    }

    public boolean isStato() {
        return stato;
    }
}
