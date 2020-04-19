package it.polito.tdp.alien;

/**
 * Sample Skeleton for 'Alien.fxml' Controller Class
 */



import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AlienController {
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField txtWord;
    @FXML
    private TextArea txtResult;
    @FXML
    private Button btnTranslate;
    @FXML
    private Button btnReset;
        
    
    private AlienDictionary alienD = new AlienDictionary();;
   
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert txtWord != null : "fx:id=\"txtWord\" was not injected: check your FXML file 'Alien.fxml'.";
    	assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Alien.fxml'.";
    	assert btnTranslate != null : "fx:id=\"bntTranslate\" was not injected: check your FXML file 'Alien.fxml'.";
    	assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Alien.fxml'.";
    	
    }
  
    
    @FXML
    void doTranslate(ActionEvent event) {
    		
	    	if (txtWord.getText().equals("")) {
			txtResult.appendText("Inserisci una parola\n");
			return;
		} 
	    	
    		StringTokenizer st = new StringTokenizer( txtWord.getText(), " ");
    		String alienWord = st.nextToken();
    		
    		if (!st.hasMoreTokens()) {
    			if (alienD.translateWord(alienWord)!=null) {
    				txtResult.appendText(alienD.translateWord(alienWord));
    			} 
    			else {
    				txtResult.appendText("Parola non esistente nel vocabolario\n");
    			}
    		} 
    		else if (st.hasMoreTokens()){
    			String translation = st.nextToken();
    			alienD.addWord(alienWord, translation);
    			txtResult.appendText(alienD.translateWord(alienWord));
    		}
    	    	
    }
    
    
    @FXML
    void doReset(ActionEvent event) {
	    	if (alienD.words.size()!=0) {
	    		alienD.words.clear();
	    	}
	    	txtWord.clear();
    		txtResult.setText("Welcome to Alien Dictionary v2018\n");
    }
    
}
