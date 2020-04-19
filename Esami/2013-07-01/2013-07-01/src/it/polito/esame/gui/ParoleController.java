package it.polito.esame.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.esame.bean.Parola;
import it.polito.esame.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ParoleController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="theRoot"
    private BorderPane theRoot; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="x1"
    private Color x1; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaIniziale"
    private TextField txtParolaIniziale; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaFinale"
    private TextField txtParolaFinale; // Value injected by FXMLLoader

    @FXML // fx:id="btnCerca"
    private Button btnCerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtLunghezza"
    private TextField txtLunghezza; // Value injected by FXMLLoader

    @FXML // fx:id="btnCarica"
    private Button btnCarica; // Value injected by FXMLLoader
    
    @FXML
    void doCarica(ActionEvent event) {
    		txtResult.clear();
    		 try {
    			 int lunghezza = Integer.parseInt(txtLunghezza.getText());
    			 model.cercaParoleLunghezza(lunghezza);
    			 txtResult.appendText("Caricate "+model.getParoleLung().size()+" parole, di lunghezza "+lunghezza+"\n");
    			 txtResult.appendText("Creato grafo con "+model.getGrafo().vertexSet().size()+" vertici e "
    			 		+ ""+model.getGrafo().edgeSet().size()+" archi.\n");
    			 txtResult.appendText("La parola "+model.getParolaMaxConnected().getNome()+" ha "+model.getMaxConnection()+" parole connesse");
    			 
    		 }  catch(NumberFormatException nfe) {
    			 txtResult.appendText("Inserisci una lunghezza intera, per cercare le parole");
		 }  catch(RuntimeException rne) {
 			txtResult.appendText("Errore connessione database");
 		}

    }

    @FXML
    void doCerca(ActionEvent event) {
    		txtResult.clear();
    	
    		try {
    			int lunghezza = Integer.parseInt(txtLunghezza.getText());
    			String parolaPartenza = txtParolaIniziale.getText();
    			String parolaArrivo = txtParolaFinale.getText();
    			
    			if (parolaPartenza==null|| parolaArrivo==null) {
    				txtResult.appendText("Inserisci le parole!");
    			}
    			model.cercaParoleLunghezza(lunghezza);
    			if (model.isOk(parolaPartenza, lunghezza) && model.isOk(parolaArrivo, lunghezza)) {
    				txtResult.appendText("Caricate "+model.getParoleLung().size()+" parole, di lunghezza "+lunghezza+"\n");
    	   			txtResult.appendText("Creato grafo con "+model.getGrafo().vertexSet().size()+" vertici e "
    	   			 		+ ""+model.getGrafo().edgeSet().size()+" archi.");
    	   			
    				List<Parola> sequenza = model.cercaSequenza(parolaPartenza, parolaArrivo);
    				for (Parola p : sequenza) {
    					txtResult.appendText(p.getNome()+"\n");
    				}
    			}
    			else {
    				txtResult.appendText("Parole non presenti nel dizionario / lunghezza sbagliata : riprova.");
    			}
    			
    		} catch(NumberFormatException nfe) {
    			 txtResult.appendText("Inserisci due parole valide e una lunghezza coerente");
    		} catch(RuntimeException rne) {
    			txtResult.appendText("Errore connessione database");
    		}

    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtParolaIniziale != null : "fx:id=\"txtParolaIniziale\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtParolaFinale != null : "fx:id=\"txtParolaFinale\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert btnCarica != null : "fx:id=\"btnCarica\" was not injected: check your FXML file 'paroleT1.fxml'.";

    }

	public void setModel(Model model) {
		this.model= model;
		
	}
}
