package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.CoppiaRaces;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Season> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	
    		txtResult.clear();
    		try {
    			Season season = this.boxAnno.getValue();
    			if (season == null) {
    				txtResult.appendText("Scegli una stagione.\n");
    				return;
    			}
    			List<CoppiaRaces> result = new ArrayList<>(model.getMaxArco(season));
    			if (result.size() == 0) {
    				txtResult.appendText("Lista vuota : nessuna coppia di Races ha partecipanti comuni");
    				return;
    			}
    			
    			txtResult.appendText("Coppie di Races con max numero di driver comuni della stagione :" +season.getYear()+" \n");
    			for (CoppiaRaces cr : result)
    				txtResult.appendText(cr.toString()+"\n");
    			this.boxGara.getItems().addAll(model.getRacesForSimulation(season));
    		
    		}catch(RuntimeException e) {
    			txtResult.appendText("errore db");
    		}
    
    	
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    		txtResult.clear();
		try {
			Race race = this.boxGara.getValue();
			if (race == null) {
				txtResult.appendText("Scegli una gara.");
				return;
			}
			double P = Double.parseDouble(this.textInputK.getText());
			int T = Integer.parseInt(this.textInputK1.getText());
		
			model.simula(race, P, T);
			Map<Integer, Driver> map = new HashMap<Integer,Driver	>(model.getSimulatore().getMigliorDriver());
			
			for (Integer integer : map.keySet()) {
				System.out.println("giro " +integer.toString() + " :  "+map.get(integer).toString());
			}
			
		}catch (NumberFormatException nfe) {
			txtResult.appendText("Inserisi un valore di probabilita P come numero con le virgola (es: 0.45) e un valore di T secondi intero (es: 20)");
		}catch(RuntimeException e) {
			txtResult.appendText("errore db");
		} 
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.getSeasons());
		
	}
}
