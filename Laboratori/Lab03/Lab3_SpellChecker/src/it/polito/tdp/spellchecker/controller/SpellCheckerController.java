/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class SpellCheckerController {

	Dictionary dictionary ;
	List<RichWord> spellChecker  = new ArrayList<RichWord>();
	List<String> inputTextList = new ArrayList<String>();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="langChoose"
    private ChoiceBox<String> langChoose; // Value injected by FXMLLoader

    @FXML // fx:id="textField"
    private TextArea textField; // Value injected by FXMLLoader

    @FXML // fx:id="textArea"
    private TextArea textArea; // Value injected by FXMLLoader

    @FXML // fx:id="errorCurr"
    private Text errorCurr; // Value injected by FXMLLoader
    
    @FXML // fx:id="timeSpellCheck"
    private Text timeSpellCheck; // Value injected by FXMLLoader
    
    @FXML // fx:id="spellCheck"
    private Button spellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="clearText"
    private Button clearText; // Value injected by FXMLLoader

    @FXML
    void doClearText(ActionEvent event) {
    		
     	if (textArea.getText().length()!=0 || textField.getText().length()!=0) {
    			textArea.clear();
    			textField.clear();
    			langChoose.setValue(null);
     	}
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    		//Inizializzo
    		spellChecker.clear();
    		inputTextList.clear();
    		textArea.clear();
    		int numeroParoleErrate = 0;
    		
    		//Guardo la lingua scelta
    		String lingua =  langChoose.getValue();
    		if (lingua == null) {
    			textArea.appendText("Non hai scelto la lingua!\n");
    			return;
    		} 
    		else {
    			//Carico il dizionario della lingua scelta
	    		dictionary.loadDictionary(lingua);
	    		
	    		//Leggo il testo scritto nel textField, suddividendolo in token
	    		StringTokenizer st = new StringTokenizer (textField.getText(), " ");
	    		//Salvo il testo in una lista, levando punteggiatura, spazi, e mettendo tutto in minuscolo
	    		while(st.hasMoreTokens()) {
	    			String s = st.nextToken().trim().toLowerCase().replaceAll("[\\p{Punct}]", "");
	    			inputTextList.add(s);
	    		}
	    		
	    		//Faccio lo spellCheck, controllando anche il tempo che ci impiega
	    		long l1 = System.nanoTime();
	    			//modo 1 : contains()
	    			//spellChecker = dictionary.spellCheckText(inputTextList);
	    		
	    			//modo2 : ricerca lineare
	    			spellChecker = dictionary.spellCheckTextLinear(inputTextList);
	    			
	    			//modo 3: ricerca dicotomica
	    			//spellChecker = dictionary.spellCheckTextDichotomic(inputTextList);
	    			
	    		long l2 = System.nanoTime();
	    		
	    		//Scrivo quali sono le parole sbagliate
	    		for (RichWord r : spellChecker) {
	    			if (r.isInDictionary()==false) {
	    				textArea.appendText(r.getRichWord()+"\n");
	    				numeroParoleErrate++;
	    			}
	    		}
	    		errorCurr.setText("The text contains "+ numeroParoleErrate+" errors");
	    		timeSpellCheck.setText("Spell check completed in "+(l2-l1)/1E9 +" seconds");
    		}
    }
    
    public void setDictionary () {
		this.dictionary= new Dictionary();
		langChoose.getItems().add("Inglese");
		langChoose.getItems().add("Italiano");
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert langChoose != null : "fx:id=\"langChoose\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert errorCurr != null : "fx:id=\"errorCurr\" was not injected: check your FXML file 'SpellChecker.fxml'.";

    }
}

