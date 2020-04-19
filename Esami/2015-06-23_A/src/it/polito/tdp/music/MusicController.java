package it.polito.tdp.music;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.music.model.ArtistListening;
import it.polito.tdp.music.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MusicController {

	
	Model model;
	Month month;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Month> boxMese;

    @FXML
    private Button btnArtisti;

    @FXML
    private Button btnNazioni;

    @FXML
    private TextArea txtResult;

    
    @FXML
    void doElencoArtisti(ActionEvent event) {
    		txtResult.clear();
    		try {
    			month = this.boxMese.getValue();
    			
    			if (month==null) {
    				txtResult.appendText("Scegli un mese per cominciare.\n");
    				return;
    			}
    			List<ArtistListening> primi20=new ArrayList<>(model.getPiuAscoltatiDelMese(month));
    			if (primi20.size()==0) {
    				txtResult.appendText("Errore");
    				return;
    			}
    			for (int j=0; j< primi20.size(); j++)
    				txtResult.appendText((j+1)+".  "+primi20.get(j).toString()+"\n");
    			
    			
    		} catch (RuntimeException e) {
    			txtResult.appendText("Errore database");
    		}
    }

    @FXML
    void doMaxDistanza(ActionEvent event) {
    	txtResult.clear();
    	
    	
    	try {
    		Month month2 = this.boxMese.getValue();
		
		if (month2==null) {
			txtResult.appendText("Scegli un mese per cominciare.\n");
			return;
		} if (!month2.equals(month)) {
			txtResult.appendText("Devi prima cliccare <ElencoArtisti>");
		}
		
		model.getDistanzaMax();
    	} catch (RuntimeException e) {
    		txtResult.appendText("Errore database");
	}
		
		
    }
    
    @FXML
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnArtisti != null : "fx:id=\"btnArtisti\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MusicA.fxml'.";

    }
    
    
    void setModel(Model model) {
    		this.model=model;
    		this.boxMese.getItems().addAll(Month.values());
    }
}
