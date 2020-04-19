package it.polito.tdp.meteo;
	

import it.polito.tdp.meteo.bean.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Meteo.fxml")) ;
			BorderPane root = (BorderPane)loader.load();

			Scene scene = new Scene(root);
			MeteoController controller = loader.getController() ;
			
			Model model = new Model();
			controller.setModel(model);
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
