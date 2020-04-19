package it.polito.tdp.flight;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportDistance;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    
    @FXML
    void doServiti(ActionEvent event) {
    	txtResult.clear();
	    	try {
			Airline a = this.boxAirline.getValue();
			if (a == null) {
				txtResult.appendText("Scegli un airline per trovare gli aeroporti serviti.\n");
				return;
			}
			
			Set<Airport> airports= new HashSet<Airport>(model.getServiti(a));
			
			if (airports.size() == 0) {	
				txtResult.appendText("Errore");
				return;
			}
			
			txtResult.appendText("Aeroporti serviti dalla compagnia aerea "+a.getName()+": \n");
			txtResult.appendText(airports.toString());
			
			this.boxAirport.getItems().addAll(new ArrayList<Airport>(airports));
			
		} catch (RuntimeException e) {
			txtResult.appendText("Errore database");
		}
	
    }

    @FXML
    void doRaggiungibili(ActionEvent event) {
      	txtResult.clear();
	    	try {
			Airline airline = this.boxAirline.getValue();
			if (airline == null) {
				txtResult.appendText("Scegli un airline per trovare gli aeroporti serviti.\n");
				return;
			}
			Airport airport = this.boxAirport.getValue();
			if (airport == null) {
				txtResult.appendText("Scegli un airport.\n");
				return;
			}
			List<AirportDistance> list =  new ArrayList<>(model.trovaRaggiungibili (airport, airline));
			
			if (list.size() ==0) {
				txtResult.appendText("Nessun aeroporto raggiungibile da quello selezionato");
				return;
			}
			
			txtResult.appendText("Per raggiungere da "+airport+ " : \n");
			for (AirportDistance ad: list)
				txtResult.appendText(ad.toString()+"\n");
			
		} catch (RuntimeException e) {
			txtResult.appendText("Errore database");
		}

    		
    }


    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }

	public void setModel(Model model) {
		this.model = new Model();
		this.boxAirline.getItems().addAll(model.getAirlines());
	}
}
