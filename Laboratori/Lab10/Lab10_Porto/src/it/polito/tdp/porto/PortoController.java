package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	Model m; 
	Author primoAutore;
	Author secondoAutore;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxPrimo"
    private ComboBox<Author> boxPrimo; // Value injected by FXMLLoader

    @FXML // fx:id="boxSecondo"
    private ComboBox<Author> boxSecondo; // Value injected by FXMLLoader
    
    @FXML // fx:id="coautoriButton"
    private Button coautoriButton; // Value injected by FXMLLoader

    @FXML // fx:id="sequenzaButton"
    private Button sequenzaButton; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCoautori(ActionEvent event) {
    		txtResult.clear();
    		
    		primoAutore = boxPrimo.getValue();
    		if (primoAutore == null) {
    			txtResult.appendText("Scegli un autore.");
    		}
    		else {
    			
    			Graph<Author, DefaultEdge> grafo = m.createGraph();
    			txtResult.appendText("Grafo creato, vertici : " +grafo.vertexSet().size()+", archi: "+grafo.edgeSet().size()+" \n");
    			txtResult.appendText("Coautori di "+primoAutore.toString()+":\n");
    			List<Author> coautori = m.trovaCoautori(primoAutore);
    			for (Author coautore : coautori) {
    				txtResult.appendText(coautore.toString()+"\n");
    			}
    			
    			boxSecondo.getItems().addAll(m.trovaNonCoautori(primoAutore));
    			boxSecondo.setDisable(false);
    			coautoriButton.setDisable(true);
    			sequenzaButton.setDisable(false);
    		}

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    		txtResult.clear();
    		
    		secondoAutore = boxSecondo.getValue();
    		if (secondoAutore == null) {
    			txtResult.appendText("Scegli un secondo autore.");
    		}
    		else {
    			List<Paper> pubblicazioniSequenza = new ArrayList<Paper>(m.trovaSequenza(primoAutore, secondoAutore));
    			if (m.trovaSequenza(primoAutore, secondoAutore) == null) {
    				txtResult.appendText("Nessuna sequenza trovata.");
    				return;
    			}
    			else {
    			for (Paper p : pubblicazioniSequenza)
    				txtResult.appendText(p.getTitle()+"\n");
    			}
    		}

    }

    
    public void setModel(){
    		m = new Model();
    		boxPrimo.getItems().addAll(m.getAutori());
    }
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
