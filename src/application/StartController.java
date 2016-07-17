package application;

import java.io.IOException;
		/**
		 * 	layout poczatkowy
		 * przelaczanie pomiedzy scenami 
		 * mozna dodac elementy menu
	
		 */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StartController implements Initializable {
	@FXML
	private Button btnCreate; 
	@FXML
	private Button btnDraw; 
	@FXML
	private Label info; 
	@FXML
	void goToDrawCard (){
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("DrawCard.fxml"));
		
		 Main.scene = new Scene(root,400,600);
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.stage.setScene(Main.scene);
		Main.stage.setTitle("Grywalizacja- losuj");
		Main.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void goToCreate(){
		try {
			
			GridPane layoutTalia = (GridPane) FXMLLoader.load(getClass().getResource("CardCreator.fxml")); ;
			 Main.scene = new Scene(layoutTalia,400,400);
			Main.stage.setScene(Main.scene);
			Main.stage.setTitle("Grywalizacja-O tworzenie talii");
			Main.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	static public void goToStart(){
		VBox root;
		try {
			root = (VBox)FXMLLoader.load(StartController.class.getResource("Start.fxml"));
		 Main.scene =new Scene(root, 400, 400);
		 Main.stage.setScene(Main.scene);
			Main.stage.setTitle("Grywalizacja-start");
			Main.stage.show();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
//		
//		Main.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
//		
//		primaryStage.show()
	}
	DrawCardController sampleController= new DrawCardController(); 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 
		sampleController.readDeckInfo();
		if(DrawCardController.isStarted==0){
//				scenaKarta(null);
			System.out.println("Start: takia nie rozpoczęta");
				btnDraw.setDisable(true);
		}
		// gdy jest utworzona
		else {
			System.out.println("rozpoczeta");
//				btnDraw.setDisable(false);
		} 
	}

}
