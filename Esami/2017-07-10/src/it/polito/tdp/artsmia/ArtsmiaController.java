/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLUN"
	private ChoiceBox<Integer> boxLUN; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaComponenteConnessa"
	private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaOggetti"
	private Button btnCercaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaOggetti"
	private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="txtObjectId"
	private TextField txtObjectId; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaOggetti(ActionEvent event) {
		
		try {
			model.creaGrafo();
			if (model.getGrafo() !=null) {
				txtResult.appendText(String.format("Creato grafo di %d vertici e %d archi",
						model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size()));
			}
			
			
		} catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore connessione al DataBase.");
		}
		
		
	}

	@FXML
	void doCalcolaComponenteConnessa(ActionEvent event) {
		txtResult.clear();
		try {
			int objId = Integer.parseInt(this.txtObjectId.getText());
			
			if (txtObjectId.getText() == null) {
				txtResult.appendText("Inserisci un object_id.\n");
				return;
			}
			
			if (model.getArtObject(objId)==null) {
				txtResult.appendText("ObjId non presente");
				return;
				
			} else {
				ArtObject o = model.getArtObject(objId);
				if (model.getGrafo().vertexSet().contains(o)) {
					List<ArtObject> connessi= Graphs.neighborListOf(model.getGrafo(), o);
					txtResult.appendText("Componenti connesse a ArtObject ("+o.getId()+") "+o.getTitle()+" : \n");
					for (ArtObject connesso : connessi) {
						txtResult.appendText(connesso+"\n");
						DefaultWeightedEdge e = model.getGrafo().getEdge(o, connesso);
						model.getGrafo().getEdgeWeight(e);
					}
				} else {
					txtResult.appendText("ArtObject senza alcuna componente connessa");
					return;
				}
				List<Integer> lun = new ArrayList<Integer>();
				model.popolaLun(lun, o);
				this.boxLUN.getItems().addAll(lun);
				
			}

		}catch(NumberFormatException nfe) {
			txtResult.appendText("Inserisci un numero di object_id intero.\n");
		} catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore connessione al DataBase.");
		} 
	}

	@FXML
	void doCercaOggetti(ActionEvent event) {
		txtResult.clear();
		
		try {
			int lun = this.boxLUN.getValue();
			int objId = Integer.parseInt(this.txtObjectId.getText());
			
			ArtObject o = model.getArtObject(objId);;
			
			if(this.boxLUN == null) {
				txtResult.appendText("Scegli una lunghezza dal menu a tendina");
				return;
			}
			List<ArtObject> camminoMax = model.getCammino(lun, o);
			txtResult.appendText(""+camminoMax.size());
			
		}  catch(RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore connessione al DataBase.");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		
	}
}
