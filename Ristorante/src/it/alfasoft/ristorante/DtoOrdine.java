package it.alfasoft.ristorante;

public class DtoOrdine {
    private int id_ordine;
    private String stato;
    private int id_cliente;
    private int tavolo;

    public DtoOrdine(int id_ordine, String stato, int tavolo, int id_cliente) {
        this.id_ordine = id_ordine;
        this.stato = stato;
        this.tavolo = tavolo;
        this.id_cliente = id_cliente;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public String getStato() {
        return stato;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getTavolo() {
        return tavolo;
    }
}
