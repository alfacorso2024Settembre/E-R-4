package it.alfasoft.ristorante;

public class DtoPiatto {
    private int id_piatto;
    private String nome;
    private int prezzo;
    private String descrizione;
    private int tempo_preparazione;

    public DtoPiatto(int id_piatto, String nome, int prezzo, String descrizione, int tempo_preparazione) {
        this.id_piatto = id_piatto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.tempo_preparazione = tempo_preparazione;
    }

    public int getId_piatto() {
        return id_piatto;
    }

    public String getNome() {
        return nome;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getTempo_preparazione() {
        return tempo_preparazione;
    }
    @Override
    public String toString() {
        return "Piatto{" +
                "id=" + id_piatto +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", tempoPreparazione=" + tempo_preparazione +
                '}';
    }

}
