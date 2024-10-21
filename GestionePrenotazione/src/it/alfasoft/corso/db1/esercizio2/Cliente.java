package it.alfasoft.corso.db1.esercizio2;

public class Cliente {
    private String CF;
    private String nome;
    private String cognome;

    public Cliente(String CF, String nome, String cognome) {
        this.CF = CF;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCF() {
        return CF;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
}
