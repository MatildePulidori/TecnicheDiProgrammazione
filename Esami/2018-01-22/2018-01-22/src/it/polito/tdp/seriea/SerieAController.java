/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.*;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamGoalsSeason;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
public class SerieAController {

	Model model ;
	
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSquadra"
    private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaSquadra"
    private Button btnSelezionaSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaAnnataOro"
    private Button btnTrovaAnnataOro; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaCamminoVirtuoso"
    private Button btnTrovaCamminoVirtuoso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    		txtResult.clear();
    		try {
    			Team team =  this.boxSquadra.getValue();
    			if (team == null) {
    				txtResult.appendText("Scegli una squadra");
    				return;
    			}
    			
    			List<TeamGoalsSeason> listaPuntiClassifica = new ArrayList<TeamGoalsSeason>(model.calcolaPuntiInClassifica(team));
    			
    			if (listaPuntiClassifica.size() == 0) {
    				txtResult.appendText("La squadra non ha partecipato a nessuna stagione di campionato.");
    				return;
    			}
    			txtResult.appendText(listaPuntiClassifica.toString());
    			
    		} catch(RuntimeException e) {
    			txtResult.appendText("Errore database");
    		}

    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    		txtResult.clear();
		try {
			Team team =  this.boxSquadra.getValue();
			if (team == null) {
				txtResult.appendText("Scegli una squadra");
				return;
			}
			
			TeamGoalsSeason annataOro = model.trovaAnnataOro(team);
			
			if (annataOro == null) {
				txtResult.appendText("Errore");
				return;
			}

			System.out.println("Grafo creato, "+model.getGrafo().vertexSet().size()+" vertici, "+model.getGrafo().edgeSet().size()+"archi. \n");
			txtResult.appendText(annataOro+", con "+ model.getMaxPunteggio()+" punti.");
			
		}catch(RuntimeException e) {
			txtResult.appendText("Errore database");
		}

    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	
    		txtResult.clear();
		try {
			Team team =  this.boxSquadra.getValue();
			if (team == null) {
				txtResult.appendText("Scegli una squadra");
				return;
			}
			List<TeamGoalsSeason> virtousPath= new ArrayList<TeamGoalsSeason>(model.camminoVirtuoso(team));
			if (virtousPath.size()==0) {
				txtResult.appendText("Errore ricerca cammino virtuoso .\n");
				return;
			}
			
			txtResult.appendText(virtousPath.toString().toString());
			
		} catch(RuntimeException e) {
				txtResult.appendText("Errore database");
		}


    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxSquadra.getItems().addAll(this.model.getSquadre());
		
	}
}
