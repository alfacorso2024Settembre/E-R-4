package it.alfasoft.corso.esercizio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class UIfitness {
    public DtoUtente creaUtente(){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci il nome dell'utente:");
        String nome = input.nextLine();
        System.out.println("Inserisci il codice fiscale dell'utente:");
        String cf = input.nextLine();
        return new DtoUtente(nome,cf);
    }
    public DtoObiettivo creaObiettivo(DtoUtente utente){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci la descrizione dell'obiettivo:");
        String descrizione = input.nextLine();
        System.out.println("Inserisci la data di inizio:");
        String data_inizio = input.nextLine();
        System.out.println("Inserisci la data di fine:");
        String data_fine = input.nextLine();

        return new DtoObiettivo(descrizione, Date.valueOf(data_inizio), Date.valueOf(data_fine),utente);
    }
    private DtoTipoAttivita creaTipo(){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci il nome del tipo di attivita':");
        String nome = input.nextLine();
        System.out.println("Inserisci la descrizione del tipo di attivita':");
        String descrizione = input.nextLine();
        return new DtoTipoAttivita(nome,descrizione);
    }
    public DtoAttivitaFisica creaAttivita(DtoUtente utente){
        Scanner input = new Scanner(System.in);
        ArrayList<DtoTipoAttivita> lista = new ArrayList<>();
        System.out.println("Inserisci data dell'attivita':");
        String data_attivita = input.nextLine();
        int scelta=-1;
        System.out.println("Tipi di attivita'");
        while (scelta!=0){
            lista.add(creaTipo());
            System.out.println("0)Per uscire\n1)Per continuare\nScelta:");
            scelta=input.nextInt();
        }
        return new DtoAttivitaFisica(Date.valueOf(data_attivita),utente,lista);
    }
}
