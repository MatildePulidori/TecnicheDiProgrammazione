package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    		txtResult.clear();
    		try {
    			
    			Season season = boxAnno.getValue();
    			if (season == null) {
    				txtResult.appendText("Segli una stagione.\n");
    				return;
    			}
    			model.creaGrafo(season);
    			Driver best = model.getBestDriver();
    			txtResult.appendText(String.format("Miglior driver %s",best.toString()));
    			
    			
    		} catch(RuntimeException e) {
    			e.printStackTrace();
    			txtResult.appendText("Errore connessione al DataBase.\n");
    		}

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	txtResult.clear();
		try {
			
			int k = Integer.parseInt(textInputK.getText());
			if (k<=0 ) {
				txtResult.setText("Inserire k > 0 ");
				return;
			}
			List<Driver> drivers = model.getDreamTeam(k);
			txtResult.setText(drivers.toString());
			
		} catch(NumberFormatException nfe) {
			txtResult.appendText("Inserisci un numero intero!");
			
		} catch(RuntimeException e) {
			txtResult.appendText("Errore connessione al DataBase.\n");
		}

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    		this.model = model;
    		this.boxAnno.getItems().addAll(model.getListaSeason());
    }
}
