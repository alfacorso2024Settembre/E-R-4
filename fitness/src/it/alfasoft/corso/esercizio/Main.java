package it.alfasoft.corso.esercizio;

import java.util.Scanner;

public class Main {
    private static boolean isUtente=true;
    public static void main(String[] args) {
        if(isUtente){
            UIfitness ui = new UIfitness();
            DtoUtente utente = ui.creaUtente();
            Scanner input = new Scanner(System.in);
            DaoUtente daoUtente = new DaoUtente();
            utente.setId_utente(daoUtente.getIdByUtente(utente));
            int scelta=-1;
            while (scelta!=0){
                stampaMenu();
                scelta=input.nextInt();
                switch (scelta){
                    case 0:
                        break;
                    case 1:
                        daoUtente.create(ui.creaObiettivo(utente));
                        break;
                    case 2:
                        for(DtoObiettivo ob : daoUtente.read(10,0,utente)){
                            System.out.println(ob);
                        }
                        break;
                    case 3:
                        daoUtente.create(ui.creaAttivita(utente));
                        break;
                    case 4:
                        //get rows modified
                        daoUtente.delete(ui.creaObiettivo(utente));
                        break;
                    default:
                        break;
                }
            }

        }
    }
    public static void stampaMenu(){
        System.out.println("1)Crea obiettivo\n2)Visualizza obiettivi\n3)Esegui attivita'\n4)Elimina obiettivo\n0)Esci\nScelta:");
    }
}