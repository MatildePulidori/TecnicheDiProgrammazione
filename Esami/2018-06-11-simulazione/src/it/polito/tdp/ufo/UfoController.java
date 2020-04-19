/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.ufo.model.Model;
import it.polito.tdp.ufo.model.State;
import it.polito.tdp.ufo.model.YearSights;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {

	Model model; 
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<YearSights> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<State> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

   

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    		txtResult.clear();
		try {
			YearSights yS = boxAnno.getValue();
			
			if (yS==null) {
				txtResult.appendText("Scegli un anno");
				return;
			}
			
			model.creaGrafo(yS);
			txtResult.appendText(String.format("Creato grafo con %d vertici e %d archi", 
					model.getGrafo().vertexSet().size(),model.getGrafo().edgeSet().size() ));
			boxStato.getItems().clear();
			boxStato.getItems().addAll(model.getStati(yS));
			
		} catch (RuntimeException e) {
			txtResult.appendText("Errore connessione database");
		}
    }

    @FXML
    void handleAnalizza(ActionEvent event) {
    		txtResult.clear();
	    	try {
	    		State state = this.boxStato.getValue();
	    		
	    		if (state == null) {
	    			txtResult.appendText("Scegli uno stato per analizzare.\n");
	    			return;
	    		}
	    		
	    		Set<State> box = new HashSet<State>( this.boxStato.getItems());
	    		Set<State> statiAnno = new HashSet<State>(model.getStati(this.boxAnno.getValue()));
	    		
	    		if(!box.equals(statiAnno) ){
	    			txtResult.appendText("Aggiorna lista stati, cliccando sul bottone <Avvistamenti>\n");
	    			return;
	    		}
				
	    		
	    		model.analizza(state);
	    		if (model.successori()==null)
	    			txtResult.appendText("Lo stato " + state.getState()+" non è seguito da alcun altro stato.\n");
	    		txtResult.appendText("Successori : " +model.successori().toString()+"\n");
	    		if (model.predecessori()==null)
	    			txtResult.appendText("Lo stato " + state.getState()+" non è precedutoo da alcun altro stato.\n");
	    		txtResult.appendText("Predecessori : " +model.predecessori().toString()+"\n");
	    		if (model.raggiungibili()==null)
	    			txtResult.appendText("Lo stato "+state.getState()+" non ha altri stati raggiungibili oltre a quelli a lui connessi.\n" );
	    		txtResult.appendText("Raggiungibili : " +model.raggiungibili().toString()+"\n");
	    		 
	    	}catch (RuntimeException e) {
				txtResult.appendText("Errore connessione database");
		}
	    
    }
    
    @FXML
    void handleSequenza(ActionEvent event) {
	    txtResult.clear();
	    	
	    	try {
	    		State state = this.boxStato.getValue();
	    		
	    		if (state == null) {
	    			txtResult.appendText("Scegli uno stato per analizzare.\n");
	    			return;
	    		}
	    		List<State> sequenzaPiuLunga = new ArrayList<State>(model.seqAvvistamenti(state));
	    		
	    		if (sequenzaPiuLunga.size()==1) {
	    			txtResult.appendText("Lo stato non ha alcun successore dopo di sè, siamo alla fine del percorso degli alieni!");
	    			return;
	    		}
	    		txtResult.appendText("Sequenza più lunga trovata: \n");
	    		txtResult.appendText(sequenzaPiuLunga.toString());
	    		
	    		
	    	} catch (RuntimeException e) {
	    		txtResult.appendText("Errore connessione databse");
	    	}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
    
    public void setModel(Model model) {
    		this.model= model;
    		boxAnno.getItems().addAll(model.getYearSights());
    }
}
