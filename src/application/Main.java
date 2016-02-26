package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

		// nazwa = "typowa talia";
		// ileKart = 40;
		// ileMalych = 6;
		// ileSrednich = 3;
/**
 * Main uruchamianie głównego okna
 * 
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
			
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		/**
		 * 	Czytanie lyoutu poczatkowego
		 */
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Start.fxml"));
			 scene =new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//			BorderPane root = loader.load(); 
//			CardController contOpen = (CardController) loader.getController(); 
//			contOpen.init(primaryStage);
//			scene = new Scene(root,400,600);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			windowLosuj.setScene(scene);
//			windowLosuj.setTitle("Grywalizacja- losuj");
//			windowLosuj.setOnCloseRequest(e->controller.zapisz(null));
//			windowLosuj.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
