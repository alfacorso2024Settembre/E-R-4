package sistemaBancario.dto;

public class DtoConto {
    private int numero_conto;
    private String iban;
    private double saldo;
    private String banca;

    public DtoConto(String iban, double saldo, String banca) {
        this.iban = iban;
        this.saldo = saldo;
        this.banca = banca;
    }

    public int getNumero_conto() {
        return numero_conto;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getBanca() {
        return banca;
    }
}
