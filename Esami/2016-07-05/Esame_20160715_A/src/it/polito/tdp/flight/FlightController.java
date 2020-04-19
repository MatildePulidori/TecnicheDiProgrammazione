package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;
	private int kilometri;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		try {
			 kilometri= Integer.parseInt(this.txtDistanzaInput.getText());
			if (kilometri==0) {
				txtResult.appendText("Inserisci dei kilometri.\n");
				return;
			}
			model.creaGrafo(kilometri);
			if (model.getGrafo()==null) {
				txtResult.appendText("Errore: grafo non creato.\n");
				return;
			}
			txtResult.appendText(String.format("Grafo creato, %d vertici e %d archi \n", model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size()));
			if (model.getGrafo().vertexSet().size()==0) {
				txtResult.appendText("Per la distanza inserita non esistono rotte\n");
				return;
			}
			
			if (model.getConnections()==true) {
				txtResult.appendText("Grafo connesso: tutti gli aeroporti sono raggiungibili tra loro\n");
			}else {
				txtResult.appendText("Grafo non connesso: non tutti gli aeroporti sono raggiungibili tra loro\n");
			}
			

			Airport piulontanoDaFiumicino=model.getAeroportPiuLontano();
			txtResult.appendText("L'aeroporto piu lontano da Fiumicino raggiungibile e`: "+piulontanoDaFiumicino.getName()+" a "+model.getDistanzaMaxFiumicino()+ "km.\n");
			
			
		} catch(NumberFormatException nfe) {
			txtResult.appendText("Inserisci kilometri formato corretto (ad es : 800, 600)");
		} catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore db");
		}
		
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		txtResult.clear();
		try {
			int km = Integer.parseInt(this.txtDistanzaInput.getText());
			if (kilometri==0) {
				txtResult.appendText("Inserisci dei kilometri");
				return;
			}
			if (km!= this.kilometri) {
				txtResult.appendText("Devi prima cliccare il bottone <Seleziona rotte>, se cambi numero di Km.\n");
				return;
			}
			int K = Integer.parseInt(this.txtPasseggeriInput.getText());
			if (this.txtPasseggeriInput.getText().equals("")) {
				txtResult.appendText("Inserisci un numero intero di K passeggeri per iniziare la simulazione.\n");
			}
			
			model.doSimulazione(K);
			txtResult.appendText(model.getOutput().toString());
			
			
			
		} catch (NumberFormatException nfe) {
			txtResult.appendText("Inserisci un numero intero di K passeggeri (es: 3).\n");
		}
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
