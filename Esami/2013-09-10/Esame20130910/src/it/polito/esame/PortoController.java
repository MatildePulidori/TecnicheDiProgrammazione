package it.polito.esame;

import it.polito.porto.bean.Model;
import it.polito.porto.bean.PortoArticle;
import it.polito.porto.bean.PortoCreator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PortoController {
	
		Model model ;

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="txtIdCreator"
	    private TextField txtIdCreator; // Value injected by FXMLLoader

	    @FXML // fx:id="x1"
	    private Insets x1; // Value injected by FXMLLoader

	    @FXML // fx:id="btnCercaArticoli"
	    private Button btnCercaArticoli; // Value injected by FXMLLoader

	    @FXML // fx:id="txtIdArticolo"
	    private TextField txtIdArticolo; // Value injected by FXMLLoader

	    @FXML // fx:id="btnCercaArticolo"
	    private Button btnCercaArticolo; // Value injected by FXMLLoader

	    @FXML // fx:id="txtResult"
	    private TextArea txtResult; // Value injected by FXMLLoader
	    
	    @FXML
	    void cercaArticoli(ActionEvent event) {
	    	txtResult.clear();
		    	try {
		    		if (txtIdCreator.getText().equals("")) {
		    			txtResult.appendText("Inserire un IdCreator per cercare i suoi articoli.\n");
		    			return;
		    		}
		    		
		    		int idCreator = Integer.parseInt(this.txtIdCreator.getText());
		    		PortoCreator creator = model.getCreator(idCreator);
		    		
		    		if (creator.equals(null)) {
		    			txtResult.appendText("IdCreator cercato inesistente.\n");
		    			return;
		    		}
		    		
		    		txtResult.appendText("Creator "+idCreator+":  "+creator.toString()+"\n");
		    		
		    		List<PortoArticle> articleCreator = new ArrayList<PortoArticle>(model.getArticoliCreator (creator));
		    		
		    		if (articleCreator.size()>0) {
		    			for (PortoArticle a : articleCreator) {
		    				txtResult.appendText(a.toString()+"\n");
		    			}
		    		}
		    		
		    	} catch(NumberFormatException nfe) {
		    		txtResult.appendText("Inserire un IdCreator intero.\n");
		    	} catch(RuntimeException re) {
		    		txtResult.appendText("Errore database.\n");
		    	}
	    }

	    @FXML
	    void cercaRevisori(ActionEvent event) {
	    		txtResult.clear();
		    	try {
		    		if (txtIdCreator.getText().equals("")) {
		    			txtResult.appendText("Inserire un IdCreator per cercare i suoi articoli.\n");
		    			return;
		    		}
		    		
		    		int idCreator = Integer.parseInt(this.txtIdCreator.getText());
		    		PortoCreator creator = model.getCreator(idCreator);
		    		
		    		if (creator.equals(null)) {
		    			txtResult.appendText("IdCreator cercato inesistente.\n");
		    			return;
		    		}
		    		
		    		long eprintid =  Integer.parseInt(this.txtIdArticolo.getText());
		    		PortoArticle article = model.getArticle(eprintid);
		    		
		    		if (article.equals(null)) {
		    			txtResult.appendText("EprintId cercato inesistente.\n");
		    			return;
		    		}
		    		
		    		if (model.isOfCreator(article, creator)==false) {
		    			txtResult.appendText("Inserisci un articolo dell'autore scelto\n");
		    			return;
		    		}
		    		
		    		List<PortoCreator> reviewers = new ArrayList<PortoCreator>(model.cercaRevisori(creator, article));
		    		
		    		if (reviewers.equals(null)) {
		    			txtResult.appendText("Non ci sono revisori disponibili.\n");
		    			return;
		    		}
		    		
		    		for (PortoCreator pc : reviewers) {
		    			txtResult.appendText(pc.toString()+" \n");
		    		}
			    		
		    		
		    	} catch(NumberFormatException nfe) {
		    		txtResult.appendText("Inserire un IdCreator intero.\n");
		    	} catch(RuntimeException re) {
		    		txtResult.appendText("Errore database.\n");
		    	}

	    }

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert txtIdCreator != null : "fx:id=\"txtIdCreator\" was not injected: check your FXML file 'peers.fxml'.";
	        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'peers.fxml'.";
	        assert btnCercaArticoli != null : "fx:id=\"btnCercaArticoli\" was not injected: check your FXML file 'peers.fxml'.";
	        assert txtIdArticolo != null : "fx:id=\"txtIdArticolo\" was not injected: check your FXML file 'peers.fxml'.";
	        assert btnCercaArticolo != null : "fx:id=\"btnCercaArticolo\" was not injected: check your FXML file 'peers.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'peers.fxml'.";

	    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
		
	}

}
