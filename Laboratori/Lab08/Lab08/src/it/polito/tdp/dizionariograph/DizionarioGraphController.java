package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="generaGrafoButton"
    private Button generaGrafoButton; // Value injected by FXMLLoader

    @FXML // fx:id="trovaViciniButton"
    private Button trovaViciniButton; // Value injected by FXMLLoader

    @FXML // fx:id="trovaGradoMaxButton"
    private Button trovaGradoMaxButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="numLettere"
    private TextField numLettere; // Value injected by FXMLLoader

    @FXML // fx:id="parolaInput"
    private TextField parolaInput; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doGrafo(ActionEvent event) {
    		txtResult.clear();
    		parolaInput.clear();
    		
    		try {
    			int nLettere = Integer.parseInt(numLettere.getText().trim());
    			if (nLettere == 0) {
    				txtResult.appendText("Inserisci il numero di lettere!");
    			}
	    		Graph<String, DefaultEdge> grafo= model.createGraph(nLettere);
	    		if (grafo !=null) {
	    			txtResult.appendText("Il grafo ha "+grafo.edgeSet().size()+" vertici");
	    			trovaViciniButton.setDisable(false);	   
	    			trovaGradoMaxButton.setDisable(false);
	    			parolaInput.setEditable(true);
	    			numLettere.setDisable(true);
	    			generaGrafoButton.setDisable(true);
	    		}
    		}
    		catch(NumberFormatException e ) {
    			txtResult.appendText("Inserisci un numero di lettere!");
    		}
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    		txtResult.clear();
		try {
			String parola = parolaInput.getText().trim().toLowerCase();
			if (parola.equals("")) {
				txtResult.appendText("Inserisci la parola di cui crecare le parole simili!");
				return;
			}
    			List<String> viciniParola = model.displayNeighbours(parola);
    		
	    		if (viciniParola.size()==0) {
	    			txtResult.appendText("Non esistono parole simili a quella inserita, controlla di aver fatto errori di battitura!");
	    			return;
	    		}
    		
	    		txtResult.appendText("Le parole simili sono: \n");
	    		for (String vicina:  viciniParola) {
	    			txtResult.appendText(vicina+"\n");
	    		}
	    		txtResult.appendText("Grado:" +viciniParola.size());
		}
		catch(NumberFormatException e ) {
			txtResult.appendText("Inserisci la parola da cercare!");
		}
	}
    
    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    		txtResult.clear();
		try {
			int grado = model.findMaxDegree();
			txtResult.appendText("Il grado massimo del grafo è: "+model.parolaMaxGrado+", di grado "+grado);
		}
		catch(NumberFormatException e ) {
			txtResult.appendText("Inserisci un numero di lettere o la parola da cercare!");
		}
    }
    
    public void setModel(Model model) {
    		this.model = model;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert numLettere != null : "fx:id=\"numLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert parolaInput != null : "fx:id=\"parolaInput\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
}
