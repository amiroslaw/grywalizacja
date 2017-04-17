package application;
	
import java.util.Locale;

import controller.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
		// nazwa = "typowa talia";
		// ileKart = 40;
		// ileMalych = 6;
		// ileSrednich = 3;
/**
 * 
 * @author Arkadiusz Mirosław
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		ViewManager viewManager = new ViewManager(primaryStage);
		viewManager.showStart();
	}
	
	public static void main(String[] args) {
	    Locale.setDefault(new Locale("en"));
//		DBmanager.createDB();
//		DBmanager.readListOfDecks();
	  
		launch(args);
		  
	}
}
