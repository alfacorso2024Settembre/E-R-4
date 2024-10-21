package sistemaBancario;

import sistemaBancario.dao.DaoConto;
import sistemaBancario.dto.DtoCliente;
import sistemaBancario.dto.DtoConto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Random;
import java.util.Scanner;

public class Banca {

    static DaoConto daoConto = new DaoConto();
    static Scanner scanner = new Scanner(System.in);
    static String codice_utente;

    public static void main(String[] args) throws SQLException {

        validazioneUtente();
        boolean exit = false;

        while(!exit) {
            System.out.println();
            System.out.println("Scegli un'opzione: ");
            System.out.println("1. Apri conto");
            System.out.println("2. Chiudi conto");
            System.out.println("3. Effettua transazione");
            System.out.println("4. Vedi saldo");
            System.out.println("5. Esci");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    apriConto();
                    break;
                case 2:
                    chiudiConto();
                    break;
                case 3:
                    System.out.print("Causale: ");
                    String causale = scanner.nextLine();
                    System.out.print("Importo: ");
                    double importo = scanner.nextDouble();
                    System.out.print("Data (YYYY-MM-DD): ");
                    String data = scanner.next();
                    System.out.print("Inserisci il tuo numero di conto:");
                    int numeroConto = scanner.nextInt();
                    Date dataTransazione = Date.valueOf(data);
                    System.out.print("Codice destinatario: ");
                    String codiceDestinatario = scanner.next();
                    daoConto.effettuaTransazione(causale, importo, dataTransazione, codice_utente, numeroConto, codiceDestinatario);
                    break;
                case 4:
                    System.out.print("Inserisci numero conto: ");
                    int numeroContoVs = scanner.nextInt();
                    daoConto.vediSaldo(numeroContoVs,codice_utente);
                    break;
                case 5:
                    System.out.println("Ciao");
                    exit = true;
                    break;
                default:
                    System.out.println("Riprova.");
                    break;
            }
        }

    }
    public static void validazioneUtente() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        do {
            int login=0;
            System.out.println("Digita 1 per autenticarti o 2 per registarti: ");
            login = scanner.nextInt();
            scanner.nextLine();
            if(login==1) {
                try {
                    System.out.println("Autenticati:");
                    System.out.print("Inserisci codice utente: ");
                    codice_utente = scanner.nextLine();
                    System.out.print("Inserisci pin: ");
                    String pin = scanner.nextLine();
                    if (daoConto.login(codice_utente,pin)) {
                        System.out.println("Bentornato!");
                        valid = true;
                    } else {
                        System.out.println("Utente non trovato o pin errato, riprova o registrati:");
                    }
                }catch (SQLException e){
                    System.out.println("errore");
                }
            } else if (login==2) {
                System.out.println("Registrati:");
                System.out.print("Il tuo codice utente: ");
                codice_utente = daoConto.generaCU();
                System.out.println(codice_utente);
                System.out.print("Inserisci pin:");
                String pin = scanner.nextLine();
                try{
                    daoConto.add(new DtoCliente(codice_utente,pin));
                    System.out.println("Hai effettuato la registrazione");
                    valid= true;
                }catch (SQLException e){
                    System.out.println("Registrazione fallita, codice utente esistente");
                }
            }
        }while (!valid);
    }

    public static void apriConto() {
        try {
            System.out.print("Inserisci saldo iniziale: ");
            double saldoIniziale = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Inserisci nome banca: ");
            String banca = scanner.nextLine();
            String iban = generaIban();
            int numeroConto = daoConto.apriConto(new DtoConto(iban, saldoIniziale, banca),codice_utente);
            System.out.println("Conto " + numeroConto + " con IBAN: " + iban);
        } catch (SQLException e) {
            System.out.println("Errore nell'apertura del conto: " + e.getMessage());
        }
    }

    public static void chiudiConto() {
        System.out.print("Inserisci numero conto da chiudere: ");
        int numeroConto = scanner.nextInt();
        scanner.nextLine();
        try {
            daoConto.chiudiConto(numeroConto,codice_utente);
        } catch (SQLException e) {
            System.out.println("Errore nella chiusura del conto: " + e.getMessage());
        }
    }

    private static String generaIban() {
        // Generate a random 26-character IBAN
        Random random = new Random();
        StringBuilder iban = new StringBuilder("IT");
        for (int i = 0; i < 24; i++) {
            iban.append(random.nextInt(10));
        }
        return iban.toString();
    }

}
