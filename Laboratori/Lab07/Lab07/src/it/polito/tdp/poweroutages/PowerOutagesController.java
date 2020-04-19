package it.polito.tdp.poweroutages;


import java.net.URL;
import java.util.*;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtMaxYears"
    private TextField txtMaxYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxHours"
    private TextField txtMaxHours; // Value injected by FXMLLoader
    
    @FXML // fx:id="listaNerc"
    private ComboBox<Nerc> listaNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    @FXML
    void doWorstCase(ActionEvent event) {
    		
		txtResult.clear();
		
	    	try {
	    		Nerc nerc = this.listaNerc.getValue();
		    	int maxYears = Integer.parseInt(txtMaxYears.getText());
		    	int maxHours = Integer.parseInt(txtMaxHours.getText());
		    
		    	if (nerc == null) {
		    		txtResult.appendText("Scegli un NERC.\n");
		    	}
		    	if (maxYears == 0) {
		    		txtResult.appendText("Inserisci anno massimo.\n");
		    	}
		    	if (maxHours == 0) {
		    		txtResult.appendText("Inserisci ore massime.\n");
		    	}
		    	
		    txtResult.appendText(model.doWorstCase(nerc, maxYears, maxHours));
	    	}
	    	catch(NumberFormatException nfe) {
	    		txtResult.appendText("Inserisci anno o ora interi.\n");
	    	}
    }
    
    
	public void setModel() {
		
		txtMaxYears.clear();
		txtMaxHours.clear();
		txtResult.clear();
		
		model = new Model();
		List<Nerc> nerc = new ArrayList<Nerc>(model.getNercList());
		listaNerc.getItems().addAll(nerc);
		
	}

	
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtMaxYears != null : "fx:id=\"txtMaxYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

}
