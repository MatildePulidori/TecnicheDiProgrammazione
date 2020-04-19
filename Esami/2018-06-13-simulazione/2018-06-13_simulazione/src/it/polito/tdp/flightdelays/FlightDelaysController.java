package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Model;
import it.polito.tdp.flightdelays.model.Tratta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class FlightDelaysController {

	Model model;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<Airline> cmbBoxLineaAerea;

    @FXML
    private Button caricaVoliBtn;

    @FXML
    private TextField numeroPasseggeriTxtInput;

    @FXML
    private TextField numeroVoliTxtInput;
    
  

    @FXML
    void doCaricaVoli(ActionEvent event) {
    	txtResult.clear();
    	try {
    		if (this.cmbBoxLineaAerea.getValue() == null) {
    			txtResult.appendText("Scegli una linea aerea.\n");
    			return;
    		}
    		
    		
    		Airline a = this.cmbBoxLineaAerea.getValue();
    		
    		model.cercaVoli(a);
    		
    		if (model.getLe10Peggiori()!=null) {
	    		for (Tratta t : model.getLe10Peggiori()) {
	    			txtResult.appendText(t.toString()+"\n");
	    		}
    		}
    		
    		
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Formate airline sbagliato.\n");
    	} catch (RuntimeException e) {
    		txtResult.appendText("Errore database");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	try {
    		
    		int K = Integer.parseInt(numeroPasseggeriTxtInput.getText());
    		int V = Integer.parseInt(numeroVoliTxtInput.getText());
    	
    		if (K!=0 && V!=0) {
    			
    			if (this.cmbBoxLineaAerea.getValue() == null) {
        			txtResult.appendText("Scegli una linea aerea.\n");
        			return;
        		}
    			
        		Airline a = this.cmbBoxLineaAerea.getValue();
        		
    			model.simula(K, V, a);
    			model.getSimulatore().run();
    			txtResult.appendText(model.getRitardoSimualazione());
    		
    			
    		}
    		else {
    			txtResult.appendText("Inserisci un numero passeggeri e/o un numero voli");
    		}
    		
    	}catch( NumberFormatException nfe) {
    		txtResult.appendText("Inserisci numero passeggeri e numero voli in formato intero");
    	} catch (RuntimeException e) {
    		txtResult.appendText("Errore database");
    	}
    		
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxLineaAerea != null : "fx:id=\"cmbBoxLineaAerea\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert caricaVoliBtn != null : "fx:id=\"caricaVoliBtn\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroPasseggeriTxtInput != null : "fx:id=\"numeroPasseggeriTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }
    
	public void setModel(Model model) {
		this.model=model;
		this.cmbBoxLineaAerea.getItems().addAll( model.getAllAirlines());
	}
}
