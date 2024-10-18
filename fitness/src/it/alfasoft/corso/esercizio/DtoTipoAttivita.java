package it.alfasoft.corso.esercizio;

public class DtoTipoAttivita {
    private int id_tipo_attivita;
    private String nome_tipo;
    private String descrizione;

    public DtoTipoAttivita(String nome_tipo, String descrizione) {
        this.nome_tipo = nome_tipo;
        this.descrizione = descrizione;
    }

    public void setId_tipo_attivita(int id_tipo_attivita) {
        this.id_tipo_attivita = id_tipo_attivita;
    }

    public int getId_tipo_attivita() {
        return id_tipo_attivita;
    }

    public String getNome_tipo() {
        return nome_tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
