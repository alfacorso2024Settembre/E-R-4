package it.alfasoft.ristorante;

import java.util.Date;

public class DtoIngrediente {
    private int id_ingrediente;
    private String nome;
    private String scadenza;

    public DtoIngrediente(int id_ingrediente, String nome, String scadenza) {
        this.id_ingrediente = id_ingrediente;
        this.nome = nome;
        this.scadenza = scadenza;
    }

    public DtoIngrediente(String nome, String scadenza) {
        this.nome = nome;
        this.scadenza = scadenza;
    }

    public int getId_ingrediente() {
        return id_ingrediente;
    }

    public String getNome() {
        return nome;
    }

    public String getScadenza() {
        return scadenza;
    }
}
