package application;
	
import java.awt.Event;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Main uruchamianie głównego okna
 * @author miro
 *
 */
public class Main extends Application {
	SampleController controller = new SampleController();
	@Override
	public void start(Stage primaryStage) {
		Stage windows= primaryStage; 
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			/**
			 * nadanie tytułu
			 */
			primaryStage.setTitle("Grywalizacja- losuj");
			primaryStage.setOnCloseRequest(e->controller.zapisz());
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
