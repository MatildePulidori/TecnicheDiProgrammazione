/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamCounterMatches;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {

	Model model; 
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxSquadra"
	private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

	@FXML // fx:id="boxStagione"
	private ChoiceBox<Season> boxStagione; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaConnessioniSquadra"
	private Button btnCalcolaConnessioniSquadra; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimulaTifosi"
	private Button btnSimulaTifosi; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaSquadre"
	private Button btnAnalizzaSquadre; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaSquadre(ActionEvent event) {
		txtResult.clear();
		try{
			model.creaGrafo();
			if (model.getGrafo() == null) {
				txtResult.appendText("Grafo vuoto.");
			}
			
			this.boxSquadra.getItems().addAll(model.getGrafo().vertexSet());
			
			
			txtResult.appendText(String.format("Creato grafo %d vertici, %d archi", 
					model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size()));
			
			this.boxStagione.getItems().addAll(model.getSeasons());
			
			
		}catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore database");
		}
	}

	@FXML
	void doCalcolaConnessioniSquadra(ActionEvent event) {
		txtResult.clear();
		try {
			Team team = this.boxSquadra.getValue();
			if (team == null) {
				txtResult.setText("Scegli squadra!");
				return;
			}
			List<TeamCounterMatches> avversariTeam = new ArrayList<TeamCounterMatches>(model.getAvversari(team));
			if (avversariTeam.size()==0) {
				txtResult.appendText("Nessun avversario.");
				return;
			}
			for (TeamCounterMatches avversario : avversariTeam) {
				txtResult.appendText(avversario.getTeamB() +" "+ avversario.getPartiteGiocate()+" \n" );
			}
			
			
			
		} catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore database");
		}
		
	}

	@FXML
	void doSimulaTifosi(ActionEvent event) {
		
		txtResult.clear();
		try {
			Season season = this.boxStagione.getValue();
			if (season == null) {
				txtResult.setText("Scegli stagione.");
				return;
			}
			model.simulaTifosi(season);
			txtResult.appendText(model.getRisultatiSimulazione());
			
		}catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore database");
		}

	}
	
	public void setModel(Model model){
		this.model = model;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

	}
}
