package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import it.polito.tdp.meteo.bean.Model;
import it.polito.tdp.meteo.bean.Situazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;


public class MeteoController {

	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Month> boxMese;

    @FXML
    private Button btnCalcola;

    @FXML
    private Button btnUmidita;

    @FXML
    private TextArea txtResult;


    @FXML
    void calcolaUmidita(ActionEvent event) {
    		txtResult.clear();
    		try {
    			Month month = this.boxMese.getValue();
    			
    			if (month==null) {
    				txtResult.appendText("Scegli un mese");
    				return;
    			}
    			List<Situazione> medie = new ArrayList<>( model.getMediaUmidita(month));
    			
    			txtResult.appendText("L'umidita` media nel mese "+month.toString()+ " e`;\n");
    			for (Situazione situazione : medie) {
    				txtResult.appendText(situazione.toString()+"\n");
    			}
    			 
    			 
    		} catch(RuntimeException e) {
    			txtResult.appendText("errore database");
    		}
    }
    @FXML
    void calcolaSequenza(ActionEvent event) {
    		txtResult.clear();
    		try {
	    		Month month = this.boxMese.getValue();
			if (month==null) {
				txtResult.appendText("Scegli un mese");
				return;
			}
			 Map<Integer,String> risultato = model.getCammino(month);
			 txtResult.appendText(risultato.toString());
			
			
		}catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("errore database");
		}

    }
    
    @FXML
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";


    }


	public void setModel(Model model) {
		this.model=model;
		this.boxMese.getItems().addAll(Month.values());
		
	}

}
