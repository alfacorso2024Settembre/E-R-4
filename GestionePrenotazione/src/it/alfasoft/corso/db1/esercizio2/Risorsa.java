package it.alfasoft.corso.db1.esercizio2;

public class Risorsa {
    private String descrizione;
    private String luogo;
    private double prezzo;

    public Risorsa(String descrizione, String luogo, double prezzo) {
        this.descrizione = descrizione;
        this.luogo = luogo;
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
