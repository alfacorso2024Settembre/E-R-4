package sistemaBancario.dto;

import java.time.LocalDate;

public class DtoPrestito {
    private int id_libro;
    private int id_cliente;
    private LocalDate dataPrestito;
    private LocalDate dataFine;

    public DtoPrestito(int id_libro, int id_cliente, LocalDate dataPrestito, LocalDate dataFine) {
        this.id_libro = id_libro;
        this.id_cliente = id_cliente;
        this.dataPrestito = dataPrestito;
        this.dataFine = dataFine;
    }

    public int getId_libro() {
        return id_libro;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public LocalDate getDataPrestito() {
        return dataPrestito;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }
}

