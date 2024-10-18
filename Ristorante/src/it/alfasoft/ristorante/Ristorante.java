package it.alfasoft.ristorante;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Ristorante {
    public static void main(String[] args) throws SQLException {/*
        Scanner scanner = new Scanner(System.in);
        boolean programma = true;

        while (programma) {
            System.out.println("Benvenuto! Scegli un'opzione:");
            System.out.println("1) Controllo Utente");
            System.out.println("2) Uscire");

            int scelta = -1;

            try {
                scelta = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input non valido. Inserisci un numero.");
                scanner.next();
                continue;
            }

            switch (scelta) {
                case 1:
                    try {
                        ConnectDB.getConn();
                        String tipoUtente = UIRistorante.chiediTipoUtente();
                        UIRistorante.controlloUtente(tipoUtente);
                    } catch (SQLException e) {
                        System.out.println("Errore nella connessione al database: " + e.getMessage());
                    }
                    break;
                case 2:
                    programma = false;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }*/

        Connection conn = ConnectDB.getConn();
        DtoPiatto piatto = new DtoPiatto(10,"pizza", 10, "aaa", 15);
        DtoPiatto piatto2 = new DtoPiatto(100,"pizza", 10, "aaa", 15);
        /*DtoOrdine ordine = new DtoOrdine(100, "PRONTO", 2, 1);
        DtoCliente cliente= new DtoCliente(1000, "2001-12-03", "fabio");*/
        //DaoCliente.vediMenu(10,0);
        //DaoCliente.vediPiattiTollerabili(10,0);

        //DaoCliente.listaPiatti();
        DaoProprietario.inserisciPiatto(piatto2);
       /*List<DtoPiatto> piatti = new ArrayList<>();
        piatti.add(piatto);
        DaoCliente.effettuaOrdine(1,piatti);*/

        //ConnectDB.closeConn();
        //scanner.close();
    }
    }

