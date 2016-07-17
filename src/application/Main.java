package application;
	
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
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
	DrawCardController controller = new DrawCardController();
	static public	Stage stage ; 
	static public Scene scene; 
	@Override
	public void start(Stage primaryStage) {
		 stage= primaryStage; 
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
			stage.setOnCloseRequest(e->controller.saveDB(null));
//			windowLosuj.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
