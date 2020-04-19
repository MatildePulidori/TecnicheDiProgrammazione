package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {
	
	Model model ;
	Simulatore simulatore;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSimulazione(ActionEvent event) {
    		txtResult.clear();
    		if (txtK.getText().equals("")) {
    			txtResult.appendText("Inseisci un fattore di scala\n");
    			return;
    		}
    		int k = Integer.parseInt(txtK.getText());
    		River river = boxRiver.getValue();
    	
    		if (k<0) {
    			txtResult.appendText("Inserisci una fattore di scala positivo\n");
    			return;
    		}
    		
    		this.simulatore = new Simulatore(river, k);
    		this.simulatore.run();
    		
    		txtResult.appendText(String.format("Giorni in cui non si è riuciti ad avere flowOut: %d\n", simulatore.getGiorniNo()));
    		txtResult.appendText(String.format("Cmedio della simulazione : %.2f", simulatore.getCMedio()));
    	

    }

    @FXML
    void visualizzaInfo(ActionEvent event) {
  	
    		River river = boxRiver.getValue();
    		model.getRiverFlowMesures(river);
    		this.txtStartDate.setText(river.getPrimaMisurazione().getDay().toString());
    		this.txtEndDate.setText(river.getUltimaMisurazione().getDay().toString());
    		this.txtNumMeasurements.setText(String.format("%d", river.getFlows().size()));
    		this.txtFMed.setText(String.format("%.2f", river.getMediaFlusso()));

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		boxRiver.getItems().addAll(model.getAllRivers());
	}
}

