package sistemaBancario.dto;

public class DtoCliente {
    private int id_cliente;
    private String codice_utente;
    private String pin;

    public DtoCliente(int id_cliente, String codice_utente, String pin) {
        this.id_cliente = id_cliente;
        this.pin = pin;
        this.codice_utente = codice_utente;
    }

    public DtoCliente(String codice_utente,String pin) {
        this.pin = pin;
        this.codice_utente = codice_utente;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getPin() {
        return pin;
    }

    public String getCodice_utente() {
        return codice_utente;
    }

    @Override
    public String toString() {
        return "DtoCliente{" +
                "id_cliente=" + id_cliente +
                ", pin=" + pin +
                ", codice_utente='" + codice_utente + '\'' +
                '}';
    }
}
