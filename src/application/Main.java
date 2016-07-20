package application;
	
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
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
 * Main uruchamianie głównego okna
 * 
 * @author miro
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		ViewManager viewManager = new ViewManager(primaryStage);
		viewManager.showStart();
	}
	static Connection conn=  (Connection) SqliteConnection.Connector();
		static void createDB() {
			if (conn == null) {

				System.out.println("connection not successful");
				System.exit(1);
			}
			try {
				Statement mySta = conn.createStatement();
				// usuwanie kart i tali
				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS karta (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , typ  INTEGER, tytul TEXT,  opis  TEXT,  obrazek  TEXT)");
				mySta.executeUpdate("CREATE TABLE IF NOT EXISTS talia (id INTEGER PRIMARY KEY  NOT NULL ,nazwa TEXT,ileKart INTEGER, ileMalych INTEGER,ileSrednich INTEGER, czyRozpoczeta INTEGER DEFAULT (0))");
			

				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	public static void main(String[] args) {
		createDB();
		launch(args);

	}
}
