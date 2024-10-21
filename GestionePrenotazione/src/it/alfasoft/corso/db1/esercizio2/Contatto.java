package it.alfasoft.corso.db1.esercizio2;

public class Contatto {
    private String email;
    private String indirizzo;
    private String telefono;

    public Contatto(String email, String indirizzo, String telefono) {
        this.email = email;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }
}
