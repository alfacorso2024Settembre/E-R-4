package it.alfasoft;

import java.time.LocalDate;

public class DtoBene {
    private String tipo;

    public DtoBene(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Bene{" +
                "tipo='" + tipo + '\'' +
                '}';
    }
}
