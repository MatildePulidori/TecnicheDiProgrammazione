/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.*;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="comboBox"
    private ComboBox<Country> comboBox; // Value injected by FXMLLoader
	
	@FXML // fx:id="trovaViciniButton"
    private Button trovaViciniButton; // Value injected by FXMLLoader

	
	@FXML
	void doCalcolaConfini(ActionEvent event) {
		txtResult.clear();
		try {
			if (txtAnno.getText().equals(null)) {
				txtResult.appendText("Inserisci un anno");
				return;
			}
			int anno = Integer.parseInt(txtAnno.getText());
			if (anno<1816 || anno>2016) {
				txtResult.appendText("Insersci un anno valido (1816-2016)");
				return;
			}
			Graph<Country, DefaultEdge> grafo = model.creaGrafo(anno);
			if (grafo!=null) {
				txtResult.appendText("Il grafo ha "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi\n");
				this.trovaViciniButton.setDisable(false);
				this.comboBox.setDisable(false);
				this.popolaComboBox (this.comboBox);
				Map<Country, Integer> stats = model.getCountryCounts();
				for (Country country : stats.keySet())
					txtResult.appendText(String.format("%s %d\n", country.getStateName(), stats.get(country)));		
				
			}
		}
		catch(NumberFormatException nfe) {
			txtResult.appendText("Inserisci un anno");
		}
	}
		
	
	private void popolaComboBox(ComboBox<Country> comboBox) {
		Set<Country> countries =this.model.getGraph().vertexSet();
		for (Country c: countries)
			comboBox.getItems().add(c);
	}


	@FXML
    void doTrovaVicini(ActionEvent event) {
		txtResult.clear();
		
		Country start = this.comboBox.getValue();
		
		//Se non è stato inserito nessun paese
		if (start.equals(null)) {
			txtResult.appendText("Segli un paese");
			return;
		}
		
		// e ne trovo i confinanti
		
		// METODO 1 : ricorsione
		// List<Country> connessi= new ArrayList<Country>(model.trovaConnessi(start));
		
		// METODO 2 : BreadthFirstIterator
		List<Country> connessi= new ArrayList<Country>(model.trovaConnessiBreadthFirstIterator(start));
	
		Collections.sort(connessi, new CountryOrdineAlfabeticoComparator());
		//Questo caso non dovrebbe esserci, ma adesso serve per verificare che il model lavori
		if (connessi.size()==0) {
			txtResult.appendText("Il paese non ha confinanti");
		}
		
		//Stampa comp. connesse al paese
		for(Country c : connessi ) {
			txtResult.appendText(c.toString()+"\n");
		}
    }
  
	public void setModel(Model model) {
		this.model = model;
	}
	
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}
}
