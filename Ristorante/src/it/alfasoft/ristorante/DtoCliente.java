package it.alfasoft.ristorante;

import java.util.Date;

public class DtoCliente {
    private int id_cliente;
    private String data_nascita;
    private String nome;

    public DtoCliente(int id_cliente, String data_nascita, String nome) {
        this.id_cliente = id_cliente;
        this.data_nascita = data_nascita;
        this.nome = nome;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getData_nascita() {
        return data_nascita;
    }

    public String getNome() {
        return nome;
    }
}
