package application;
	
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
	static public	Stage windowLosuj ; 
	static public Scene scene; 
	@Override
	public void start(Stage primaryStage) {
		 windowLosuj= primaryStage; 
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			scene = new Scene(root,400,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			windowLosuj.setScene(scene);
			windowLosuj.setTitle("Grywalizacja- losuj");
			windowLosuj.setOnCloseRequest(e->controller.zapisz(null));
			windowLosuj.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
