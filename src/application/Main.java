package application;
	
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import model.DBmanager;
import model.SqliteConnection;
import view.DrawCardController;
import view.ViewManager;
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
		
		DBmanager.createDB();
		DBmanager.readConf();
		launch(args);

	}
}
