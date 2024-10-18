package it.alfasoft.corso.esercizio;

import java.util.List;

public class DtoUtente {
    private int id_utente;
    private String nome_utente;
    private String cf;

    public DtoUtente(String nome_utente, String cf) {
        this.nome_utente = nome_utente;
        this.cf = cf;
    }


    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getId_utente() {
        return id_utente;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public String getCf() {
        return cf;
    }

}
