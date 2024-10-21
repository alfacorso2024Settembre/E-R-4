package it.alfasoft.corso.db1.esercizio2;
import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        try{
            GestioneContatti gcontatti = new GestioneContatti();
            GestioneRisorse grisorse = new GestioneRisorse();
            GestioneClienti gclienti = new GestioneClienti();
            GestionePrenotazioni gprenotazioni = new GestionePrenotazioni();
            int scelta=-1;
            int num=-1;
            while(scelta!=0){
                menu1();
                scelta=input.nextInt();
                switch (scelta){
                    case 1:
                        gestione(gcontatti, "contatti");
                        break;
                    case 2:
                        gestione(grisorse, "risorse");
                        break;
                    case 3:
                        gestione(gclienti, "clienti");
                        break;
                    case 4:
                        gestione(gprenotazioni, "prenotazioni");
                        break;
                    case 0:
                        break;
                    default:
                        break;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void menu1(){
        System.out.print("1)Contatti\n2)Risorse\n3)Clienti\n4)Prenotazioni\n0)Uscita\nScelta:");
    }

    public static void menu2(){
        System.out.print("1)Inserisci\n2)Stampa\n3)Modifica\n4)Elimina\n0)Esci\nScelta:");
    }
    public static void gestione(Gestione gestione,String tabella) throws SQLException{
        Scanner input = new Scanner(System.in);
        int num=-1;
        while(num!=0){
            menu2();
            num = input.nextInt();
            switch (num){
                case 0:
                    break;
                case 1:
                    gestione.insert(tabella,crea(tabella));
                    break;
                case 2:
                    gestione.stampa(tabella);
                    break;
                case 3:
                    gestione.update(tabella,getID(tabella,gestione),chooseID(tabella),getColonna(),getNuovo());
                    break;
                case 4:
                    gestione.delete(tabella,getID(tabella,gestione));
                    break;
                default:
                    break;
            }
        }

    }
    public static String chooseID(String tabella){
        String id="";
        switch (tabella){
            case "contatti":
                id="id_contatto";
            break;
            case "prenotazioni":
                id="numero_prenotazione";
                break;
            case "risorse":
                id="id_risorsa";
                break;
            case "clienti":
                id="id_cliente";
                break;
        }
        return id;
    }
    public static Object[] crea(String tabella) throws SQLException {
        Scanner input = new Scanner(System.in);
        Object[] obj={};
        switch (tabella){
            case "contatti":
                System.out.print("Inserisci email del contatto:");
                String email=input.nextLine();
                System.out.print("Inserisci indirizzo del conttato:");
                String indirizzo=input.nextLine();
                System.out.print("Inserisci telefono del contatto:");
                String telefono=input.nextLine();
                obj = new Object[]{email, indirizzo, telefono};
                break;
            case "prenotazioni":
                System.out.print("Inserisci data inizio della prenotazione:");
                String data_inizio=input.nextLine();
                Date datainizio = Date.valueOf(data_inizio.toString());
                System.out.print("Inserisci data fine della prenotazione:");
                String data_fine=input.nextLine();
                Date datafine = Date.valueOf(data_fine.toString());
                Date dataPrenotazione = Date.valueOf(LocalDate.now());
                boolean stato= dataPrenotazione.compareTo(datainizio)>0? true:false ;
                obj = new Object[]{datainizio, datafine, dataPrenotazione, stato, getID("risorse", new GestioneRisorse()),getID("clienti",new GestioneClienti())};
                break;
            case "risorse":
                System.out.print("Inserisci descrizione della risorsa:");
                String descrizione=input.nextLine();
                System.out.print("Inserisci luogo della risorsa:");
                String luogo=input.nextLine();
                System.out.print("Inserisci prezzo della risorsa:");
                int prezzo=input.nextInt();
                obj = new Object[]{descrizione, luogo, prezzo};
                break;
            case "clienti":
                System.out.print("Inserisci codice fiscale del cliente:");
                String cf=input.nextLine();
                System.out.print("Inserisci nome del cliente:");
                String nome=input.nextLine();
                System.out.print("Inserisci cognome del cliente:");
                String cognome=input.nextLine();
                obj = new Object[]{cf, nome, cognome, getID("contatti", new GestioneContatti())};
                break;
        }

        return obj;
    }
    public static int getID(String tabella,Gestione gestione) throws  SQLException{
        Scanner input= new Scanner(System.in);
        System.out.print("Inserisci elemento interessato:");
        String elem = input.nextLine();
        gestione.search(tabella,elem);
        System.out.print("Scelta:");
        int id= input.nextInt();
        return id;
    }
    public static String getColonna(){
        Scanner input= new Scanner(System.in);
        System.out.print("Scegli il campo da modificare:");
        return input.nextLine();
    }
    public static String getNuovo(){
        Scanner input= new Scanner(System.in);
        System.out.print("Scegli il nuovo valore:");
        return input.nextLine();
    }
}
