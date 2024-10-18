package it.alfasoft;

import java.sql.SQLException;
import java.text.ParseException;

public class Assicurazione {
    public static void main(String[] args) throws SQLException, ParseException {
            boolean exit = false;
            int utente = UIAssicurazione.scegliUtente();
            if (utente == 1){
                do {
                    switch (UIAssicurazione.sceltaAssicuratore()){
                        case 1: // vedi beni
                            UIAssicurazione.stampa(DaoAssicuratore.read(UIAssicurazione.getLimit(), UIAssicurazione.getOffset(), "beni"));
                            break;
                        case 2: // registra acquisto
                            DaoAssicuratore.aggiungiBene(new DtoBene(UIAssicurazione.getTipo()));
                            break;
                        case 3:
                            DaoAssicuratore.rimuoviBene(new DtoBene(UIAssicurazione.getTipo()));
                            break;
                        default:
                            exit = true;
                            break;
                    }
                }while (!exit);

            }
            else if(utente == 2){
                do {
                    switch (UIAssicurazione.sceltaCliente()){
                        case 1: // Visualizza polizze
                            UIAssicurazione.stampa(DaoCliente.visualizzaPolizza(UIAssicurazione.getLimit(), UIAssicurazione.getOffset(), new DtoCliente(UIAssicurazione.getCf())));
                            break;
                        case 2: // Assicura bene
                            DaoCliente.assicuraBene(new DtoBene(UIAssicurazione.getTipo()), new DtoCliente(UIAssicurazione.getCf(), UIAssicurazione.getNome(), UIAssicurazione.getCognome(), UIAssicurazione.getNascita()), new DtoPolizza(UIAssicurazione.getCopertura(), UIAssicurazione.getPrezzo(), UIAssicurazione.getInizioVal()));
                           break;
                        case 3: // Rinnova polizza
                            UIAssicurazione.stampa(DaoCliente.visualizzaPolizza(UIAssicurazione.getLimit(), UIAssicurazione.getOffset(), new DtoCliente(UIAssicurazione.getCf())));
                            DaoCliente.rinnovoPolizza(UIAssicurazione.getIdPolizza(), new DtoPolizza(UIAssicurazione.getCopertura(),UIAssicurazione.getPrezzo(),UIAssicurazione.getInizioVal()));
                        default:
                            exit = true;
                            break;

                    }
                }while (!exit);

            }

        }
    }