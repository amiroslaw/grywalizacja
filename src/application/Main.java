package application;
	
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import controller.DrawCardController;
import controller.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.DBmanager;
import model.SqliteConnection;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
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
