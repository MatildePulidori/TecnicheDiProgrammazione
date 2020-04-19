package it.polito.tdp.anagrammi.controller;
import it.polito.tdp.anagrammi.model.ModelAnagrammi;
import java.net.URL;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class AnagrammiController {
	
	ModelAnagrammi model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="textInput"
    private TextField textInput; // Value injected by FXMLLoader

    @FXML // fx:id="correctResult"
    private TextArea correctResult; // Value injected by FXMLLoader

    @FXML // fx:id="incorrectResult"
    private TextArea incorrectResult; // Value injected by FXMLLoader

    @FXML
    void doAnagrammi(ActionEvent event) {
    		correctResult.clear();
    		incorrectResult.clear();
    	
		try {
			String parolaInput = textInput.getText();
			Set<String> anagrammi = model.calcolaAnagrammi(parolaInput);
			
			for(String anagramma : anagrammi){
				if(this.model.isCorrect(anagramma)==true)
	    				   correctResult.appendText(anagramma+"\n");
				else
		    			incorrectResult.appendText(anagramma+"\n");
		    }
		}
		catch(RuntimeException r) {
			correctResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    }

    @FXML
    void doReset(ActionEvent event) {
    		textInput.clear();
    		correctResult.clear();
		incorrectResult.clear();
    }
    
    
    public void setModel(ModelAnagrammi model){
    		this.model= model;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'Anagrammi.fxml'.";
        assert correctResult != null : "fx:id=\"correctResult\" was not injected: check your FXML file 'Anagrammi.fxml'.";
        assert incorrectResult != null : "fx:id=\"incorrectResult\" was not injected: check your FXML file 'Anagrammi.fxml'.";

    }
}

