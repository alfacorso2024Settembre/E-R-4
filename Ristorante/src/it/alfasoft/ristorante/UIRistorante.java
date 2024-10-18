package it.alfasoft.ristorante;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UIRistorante {
    public static String chiediIntolleranze(){
        System.out.println("Che tipo di intolleranza hai?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String chiediTipoUtente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Che tipologia di utente sei? Cliente o proprietario");
        return scanner.nextLine();
    }

    public static void controlloUtente(String tipo) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        switch (tipo) {
            case "cliente":
                System.out.println("Che operazione vuoi eseguire? \n1)Effettuare un ordine \n" +
                        "2)Visualizzare il menu \n3)Visualizzare il menu in base alle tue intolleranze");
                int opC = scanner.nextInt();
                operazioniClienti(opC);
                break;
            case "proprietario":
                System.out.println("Che operazione vuoi eseguire? \n1)Aggiungere un piatto \n2)Rimuovere un piatto");
                int opP = scanner.nextInt();
                //operazioniProprietario(opP);
                break;
            default:
                System.out.println("Operazione inesistente");
        }
    }

    public static void operazioniClienti(int operazione) throws SQLException {
        switch(operazione){
            case 1:
                DaoCliente.effettuaOrdine(1, DaoCliente.listaPiatti());
                break;
            case 2:
                DaoCliente.vediMenu(10, 1);
                break;
            case 3:
                DaoCliente.vediPiattiTollerabili(10, 1);
                break;
            default: System.out.println("operazione non valida");
        }
    }

    public static List<DtoIngrediente> creaListaIngr(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("inserisci il numero di ingredienti di questo piatto:");
        int nrIngredienti = Integer.parseInt(scanner.nextLine());
        List<DtoIngrediente> ingredienti= new ArrayList<>();
        for(int i=0; i<nrIngredienti;i++){
            System.out.println("Inserisci il nome dell'ingrediente");
            String nome = scanner.nextLine();
            System.out.println("Inserisci la data di scadenza dell'ingrediente");
            String scadenza = scanner.nextLine();
            ingredienti.add(new DtoIngrediente(nome, scadenza));
        }
        return ingredienti;
    }
}
