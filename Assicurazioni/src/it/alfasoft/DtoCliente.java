package it.alfasoft;

import java.time.LocalDate;

public class DtoCliente {
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private LocalDate data_nascita;

    public DtoCliente(String codice_fiscale, String nome, String cognome, LocalDate data_nascita) {
        this.codice_fiscale = codice_fiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
    }

    public DtoCliente(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getData_nascita() {
        return data_nascita;
    }
}
