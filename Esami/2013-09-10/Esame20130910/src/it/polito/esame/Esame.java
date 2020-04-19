package it.polito.esame ;

import java.io.IOException;

import it.polito.porto.bean.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Esame extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("gui/peers.fxml")) ;
		BorderPane root = (BorderPane) loader.load() ;
		PortoController controller = loader.getController();
		
		Model model = new Model();
		controller.setModel(model);
		
		Scene scene = new Scene(root) ;
		primaryStage.setScene(scene) ;
		primaryStage.show() ;
		
	}


	public static void main(String[] args) {
		launch(args);
	}
}
