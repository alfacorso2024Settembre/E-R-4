package it.alfasoft;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UIAssicurazione {
    private static Scanner scan = new Scanner((System.in));


    public static int scegliUtente() {
        int scelta = -1;
        do{
            System.out.println("Che tipo di utente sei? ");
            System.out.println("0: Esci, 1: Assicuratore, 2: Cliente");
            scelta = scan.nextInt();
        }
        while (scelta != 0 && scelta != 1 && scelta != 2);

        return scelta;
    }

    public static int sceltaAssicuratore() {
        int scelta = -1;
        do{
            System.out.println("0: Esci, 1: Visualizza beni, 2: Aggiungi bene, 3: Rimuovi bene");
            scelta = scan.nextInt();
            scan.nextLine();
        }
        while (scelta != 0 && scelta != 1 && scelta != 2 && scelta!= 3);

        return scelta;
    }

    public static int sceltaCliente() {
        int scelta = -1;
        do{
            System.out.println("0: Esci, 1: Visualizza polizze, 2: Assicura bene, 3: Rinnova polizza");
            scelta = scan.nextInt();
        }
        while (scelta != 0 && scelta != 1 && scelta != 2 && scelta!= 3);

        return scelta;
    }

    public static int getLimit() {
        System.out.println("Quanti risultati vuoi visualizzare?");
        return scan.nextInt();
    }

    public static int getOffset() {
        System.out.println("Da quale elemento vuoi visualizzare?");
        return scan.nextInt();
    }

    public static void stampa(List<Object> read) {
        System.out.println();
        for(Object dtoLibro : read){
            System.out.println(dtoLibro);
        }
        System.out.println();
    }

    public static String getTipo() {
        scan.nextLine();
        System.out.println("Inserisci il tipo del bene.");
        return scan.nextLine();
    }


    public static LocalDate getNascita() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Inserisci la data di nascita.");
        return LocalDate.parse(scan.nextLine(),formatter);
    }

    public static String getNome() {
        System.out.println("Inserisci il nome");
        return scan.nextLine();
    }

    public static String getCognome() {
        System.out.println("Inserisci il cognome");
        return scan.nextLine();
    }

    public static String getCf() {
        scan.nextLine();
        System.out.println("Inserisci il codice fiscale.");
        return scan.nextLine();
    }


    public static int getCopertura() {
        System.out.println("Inserisci la percentuale di copertura: ");
        System.out.println("5% --> risarcimento di €3000");
        System.out.println("10% --> risarcimento di €5000");
        System.out.println("30% --> risarcimento di €10000");
        System.out.println("50% --> risarcimento di €15000");

        return scan.nextInt();
    }


    public static int getPrezzo() {
        System.out.println("Inserisci il prezzo della polizza scelta.");
        return scan.nextInt();
    }

    public static LocalDate getInizioVal() {
        scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Inserisci il giorno di inizio validita' della polizza.");
        return LocalDate.parse(scan.nextLine(), formatter);
    }

    public static int getIdPolizza() {
        System.out.println("\nScelgli l'id della polizza da rinnovare.");
        return scan.nextInt();
    }
}
