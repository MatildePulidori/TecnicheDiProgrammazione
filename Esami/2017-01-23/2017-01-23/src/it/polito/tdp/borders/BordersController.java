/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.*;


import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryStanz;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;
	Year limiteInf = Year.of(1816);
	Year limiteSup =  Year.of(2016);
	Year year;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="bntSimula"
    private Button bntSimula; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnCalcolaConfini"
    private Button btnCalcolaConfini; // Value injected by FXMLLoader
   

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    		txtResult.clear();
    		boxNazione.getItems().clear();
    		try {
    			year = Year.parse(txtAnno.getText());
    			
    			if (year.isBefore(limiteInf) || year.isAfter(limiteSup) ) {
    				txtResult.appendText("Inserisci un anno compreso tra "+limiteInf.toString()+ " e "+limiteSup.toString());
    				return;
    			}
    			List<Country> confini = new ArrayList<>(model.calcolaConfini(year));
    			if (confini.size()==0) {
    				txtResult.appendText("Controllare di aver creato correttamente il grafo");
    				return;
    			}
    			for(Country c: confini) {
    				txtResult.appendText(c.getStateName()+", stati confinanti fino al " +year.toString()+": "+c.getAdiacenti().size()+ "\n");
    			}
    			this.boxNazione.getItems().addAll(confini);
    			//this.btnCalcolaConfini.setDisable(true);
    			
    		} catch(DateTimeParseException e) {
    			txtResult.appendText("Inserire un anno in formato valido (es: 1987), compreso tra 1816 e 2006");
    		} catch (RuntimeException e) {
    			txtResult.appendText("Errore database");
    		}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    		this.btnCalcolaConfini.setDisable(false);
    		txtResult.clear();
    		try {
    			Year year2 = Year.parse(txtAnno.getText());
    			if (!year2.equals(year)) {
    				txtResult.appendText("Devi prima cliccare il bottone <Calcola confini> se cambi anno.");
    				return;
    			}
    			
    			Country country = this.boxNazione.getValue();
    			if (country== null) {
    				txtResult.appendText("Scegli una nazione di partenza.\n");
    				return;
    			}
    			model.simulazioneMigranti(country);
    			List<CountryStanz> stanziali = new ArrayList<CountryStanz>(model.getStanziali());
    			for (CountryStanz cs: stanziali) {
    				txtResult.appendText(cs.getCountry()+" "+cs.getNumStanziali()+"\n");
    			}
    			
    			
    		} catch(DateTimeParseException e) {
    			txtResult.appendText("Inserire un anno in formato valido (es: 1987), compreso tra 1816 e 2006");
    		} catch (RuntimeException e) {
    			e.printStackTrace();
			txtResult.appendText("Errore database");	
		}
    		
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
	}
}
