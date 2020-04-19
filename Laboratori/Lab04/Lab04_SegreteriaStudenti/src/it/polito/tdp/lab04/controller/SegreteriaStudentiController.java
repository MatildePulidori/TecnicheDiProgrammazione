package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.*;
import it.polito.tdp.lab04.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SegreteriaStudentiController {

	Model model ;
	List<Corso> corsi = new ArrayList<Corso>();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="sceltaCorso"
    private ComboBox<Corso> sceltaCorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="Nome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="Cognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCercaMatricola(ActionEvent event) {
    	//Pulisco la view
    		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();
		
		try {
			//Prendo matricola per trovare lo Studente
    			int matricola = Integer.parseInt(txtMatricola.getText());
    			if (matricola == 0) {
    				txtResult.appendText("Inserisci matricola\n");
    				return;
    			}
    			
    			//Cerco lo studente tramite il metodo del model
    			Studente s = model.getStudente(matricola);
    			
    			//Se non esiste la matricola nel DB
    			if (s == null) {
    				txtResult.appendText("Nessun risultato: matricola inesistente");
    				return;
    			}
    			
    			//Se lo studente esiste ritorno nome e cognome
    			txtNome.setText(s.getNome());
    			txtCognome.setText(s.getCognome());
    			
		}
    		catch (NumberFormatException e) {
    			txtResult.setText("Inserire una matricola nel formato corretto.");
    		} catch (RuntimeException e) {
    			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
    		}
    }

    @FXML
    void doReset(ActionEvent event) {
    		txtResult.clear();
    		txtNome.clear();
    		txtCognome.clear();  
    		sceltaCorso.setValue(null);
    }

    @FXML
    void doRicercaCorsi(ActionEvent event) {
    		//Pulisco la view
    		txtResult.clear();
    		
    		boolean iscrittoACorso = false;
    		boolean corsoHaStudente = false;
	
    		try {
    			//Prendo matricola per trovare lo Studente
    			int matricola = Integer.parseInt(txtMatricola.getText());  
    			if (matricola == 0) {
    				txtResult.appendText("Inserisci matricola\n");
    				return;
    			}
    			
    			//Cerco lo studente tramite il metodo nel model
    			Studente studente = model.getStudente(matricola);
    			
    			//Se lo studente non esiste nel DB
    			if (studente==null) {
    				txtResult.appendText("Nessun risultato: matricola inesistente\n");
    				return;
    			}
    			
    			// Inizializzo una lista, per i corsi a cui lo studente è iscritto
    			List<Corso> corsi = new ArrayList<Corso>();
    			// Se non è specificato alcun corso specifico, ritorno una lista di corsi richiamando il metodo del model
    			if (sceltaCorso.getValue()==null) {
    				corsi = model.corsiACuiIscrittoStudente(studente);
    				
    				StringBuilder sb = new StringBuilder();
        			for(Corso c : corsi) {
        				
        				sb.append(String.format("%-6s ", c.getCodiceInsegnamento()));
        				sb.append(String.format("%-35s\t ", c.getNomeCorso()));
        				sb.append(String.format("%-2s ", c.getCrediti()));
        				sb.append(String.format("%-1s", c.getPeriodoDidattico()));
        				sb.append("\n");
        			}
        			txtResult.appendText(sb.toString());
    			}
    			
    			// Se è specificato il corso, si vuole sapere se lo studente è iscritto a quel corso
    			else {
    				
    				Corso corso = sceltaCorso.getValue();
    				iscrittoACorso = model.studenteIscrittoACorso(corso, studente);
    				if ((model.studentiIscrittiAlCorso(corso).contains(studente))){
    					corsoHaStudente = true ;
    				}
    				
    				if (iscrittoACorso == true && corsoHaStudente == true) {
    					txtResult.appendText("Studente già iscritto a questo corso\n");
    				}
    				else {
    					txtResult.appendText("Studente non iscritto al corso\n");
    				}
    			}
    		}
    	
    		catch(RuntimeException e) {
    			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
    		}
    }

    @FXML
    void doIscrizione(ActionEvent event) {
    		//Prendo il corso selezionato
    		Corso corso = sceltaCorso.getValue();
    		
    		try {
    			if (corso == null) {
    				txtResult.appendText("Scegli corso\n");
    			}
    			
    			//Prendo la matricola per trovare lo studente
    			int matricola = Integer.parseInt(txtMatricola.getText());
    			if (matricola == 0) {
    				txtResult.appendText("Inserisci matricola\n");
    				return;
    			}
    			Studente studente = model.getStudente(matricola);
    			
    			//Se l'iscrizione va a buon fine
    			if (model.inscrizioneStudenteACorso(corso, studente)==true) {
    				txtResult.appendText("Iscrizione andata a buon fine\n");
    			}
    			//Se lo studente è già iscritto al corso
    			else if (model.inscrizioneStudenteACorso(corso, studente)==false){
    				txtResult.appendText("Studente già iscritto a questo corso \n");
    			}
    			
    		}
    		catch(NumberFormatException nfe) {
    			txtResult.setText("Inserire una matricola nel formato corretto.");
    		}
    		catch(RuntimeException e) {
    			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
    		}
    		
    } 
    
    
    @FXML
    void doRicercaIscritti(ActionEvent event) {
    		//Pulisco la view
    		txtResult.clear();
    		txtNome.clear();
    		txtCognome.clear();
    	
    		try {
    			//Prendo il corso scelto
    			Corso corso = this.sceltaCorso.getValue();
	    		if (corso==null) {
	    			txtResult.setText("Seleziona un corso\n");
	    		}
	    		
	    		// Per cercare nel model gli studenti iscritti al @corso
	    		List<Studente> studenti = model.studentiIscrittiAlCorso(corso);
	    		
	    		//Stampo gli iscritti 
	    		StringBuilder sb = new StringBuilder();
			for (Studente studente : studenti) {
				sb.append(studente.getMatricola());
				sb.append("\t"+studente.getCognome());
				sb.append("\t "+studente.getNome());
				sb.append("\n");
			}
			txtResult.appendText(sb.toString());
    		}
		catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    }
    
    
    public void setModel () {
    		this.model = new Model();
    		corsi = model.getTuttiICorsi();
    		sceltaCorso.getItems().addAll(corsi);
		
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert sceltaCorso != null : "fx:id=\"sceltaCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"Nome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"Cognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

    }
}

