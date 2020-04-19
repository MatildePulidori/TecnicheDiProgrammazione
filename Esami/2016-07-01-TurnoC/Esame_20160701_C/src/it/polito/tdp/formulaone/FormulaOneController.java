package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
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
    private ComboBox<Constructor> boxCostruttori;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    		txtResult.clear();
    		try {
    			
    			Constructor constructor = this.boxCostruttori.getValue();
    			if (constructor== null) {
    				txtResult.appendText("Scegli un costruttore");
    				return;
    			}
    			
    			Driver best = model.getBest(constructor);
    			if (best==null) {
    				txtResult.appendText("errore");
    				return;
    			}
    			txtResult.appendText(best.toString());
    			
    			
    		} catch (RuntimeException e) {
				txtResult.appendText("Errore database");
		}

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
	
	    txtResult.clear();
	    	try {
	    		Constructor constructor = this.boxCostruttori.getValue();
    			if (constructor== null) {
    				txtResult.appendText("Scegli un costruttore");
    				return;
    			}
    			if (model.getGrafo()==null) {
    				txtResult.appendText("Cliccare prima il bottone <Info costruttore>");
    				return;
    			}
    			
	    		int K = Integer.parseInt(this.textInputK.getText());
	    		if (K == 0) {
	    			txtResult.appendText("Inserisic un K");
	    			return;
	    		}
	    		
	    		List<Driver> dreamTeam = new ArrayList<Driver>(model.getDreamTeam(K));
	    		
	    		txtResult.appendText(dreamTeam.toString());
	    		
	    	} catch (NumberFormatException nfe) {
	    		txtResult.appendText("Inerisci un numero K intero.");
	    }catch (RuntimeException e) {
			txtResult.appendText("Errore database");
	}

	}
	    
    @FXML
    void initialize() {
        assert boxCostruttori != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    		this.model = model;
    		this.boxCostruttori.getItems().addAll(model.getConstructors());
    }
}
