package it.polito.esame;
/**
 * Sample Skeleton for 'iscrittiT1.fxml' Controller Class
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.esame.bean.Corso;
import it.polito.esame.bean.Model;
import it.polito.esame.bean.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class IscrittiController {
	
	Model model;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="x1"
    private Color x1; // Value injected by FXMLLoader

    @FXML // fx:id="btnElencoCorsi"
    private Button btnElencoCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudentiSimili"
    private Button btnStudentiSimili; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void trovaElencoCorsi(ActionEvent event) {
    		txtResult.clear();
    		try {
    			int matricola = Integer.parseInt(txtMatricola.getText());
    			
    			Studente s = model.getStudente(matricola);
    			
    			if (s==null) {
    				txtResult.appendText("Lo studente non è presente.\n");
    				return;
    			}
    			List<Corso> corsiResult = new ArrayList<Corso>(model.getCorsiStudente(s));
    			for (Corso c: corsiResult) {
    				txtResult.appendText(String.format("%s \n", c.toString()));
    			}
    			
    		} catch(NumberFormatException nfe) {
    			txtResult.appendText("Inserisci una matricola in formato solo numerico (senza la s davanti!)\n");
    			return;
    		} catch (RuntimeException e) {
    				txtResult.appendText("SQL exception\n");
    				return;
    			}

    }

    @FXML
    void trovaStudentiSimili(ActionEvent event) {
    		txtResult.clear();
		try {
			int matricola = Integer.parseInt(txtMatricola.getText());
			
			Studente s = model.getStudente(matricola);
			
			if (s==null) {
				txtResult.appendText("Lo studente non è presente.\n");
				return;
			}
			model.getCorsiStudente(s);
			List<Studente> simili = new ArrayList<Studente>(model.trovaSimili(s));
			Collections.sort(simili);
			txtResult.appendText(String.format("Numero di corsi massimi %d: \n", model.maxCorsiInsieme));
			for (Studente simile : simili) {
				txtResult.appendText(simile.toString()+"\n");
			}
			
		} catch(NumberFormatException nfe) {
			txtResult.appendText("Inserisci una matricola in formato solo numerico (senza la s davanti!)\n");
			return;
		} catch (RuntimeException e) {
			txtResult.appendText("SQL exception\n");
			return;
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'iscrittiT1.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'iscrittiT1.fxml'.";
        assert btnElencoCorsi != null : "fx:id=\"btnElencoCorsi\" was not injected: check your FXML file 'iscrittiT1.fxml'.";
        assert btnStudentiSimili != null : "fx:id=\"btnStudentiSimili\" was not injected: check your FXML file 'iscrittiT1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'iscrittiT1.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
	}
}
