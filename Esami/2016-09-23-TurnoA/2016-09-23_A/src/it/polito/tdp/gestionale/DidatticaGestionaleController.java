package it.polito.tdp.gestionale;

import java.net.URL;
import java.util.*;

import it.polito.tdp.gestionale.model.Corso;
import it.polito.tdp.gestionale.model.FrequentazioneCorso;
import it.polito.tdp.gestionale.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DidatticaGestionaleController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtMatricolaStudente;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCorsiFrequentati(ActionEvent event) {
		txtResult.clear();
		try {

			List<FrequentazioneCorso> mapResult = new ArrayList<FrequentazioneCorso>(model.statistiche());
			
			for (FrequentazioneCorso fc : mapResult) {
				txtResult.appendText(fc.toString() );
			}
			
		} catch(RuntimeException e ) {
			txtResult.appendText("Errore database.\n");
		}

		
	}
	
	@FXML
	void doVisualizzaCorsi(ActionEvent event) {
		txtResult.clear();
		try {
			
			 List<Corso> bestCammino= model.cercaNumCorsiMinimo();
			 if (bestCammino.size()!=0)
				 txtResult.appendText(bestCammino.toString());
			
		} catch(RuntimeException e ) {
			txtResult.appendText("Errore database.\n");
		}

		
	}

	@FXML
	void initialize() {
		assert txtMatricolaStudente != null : "fx:id=\"txtMatricolaStudente\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}

}
