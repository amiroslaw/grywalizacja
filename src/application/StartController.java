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
	void goToLos (){
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		
		 Main.scene = new Scene(root,400,600);
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.windowLosuj.setScene(Main.scene);
		Main.windowLosuj.setTitle("Grywalizacja- losuj");
		Main.windowLosuj.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	void goToCreate(){
		try {
			
			GridPane layoutTalia = (GridPane) FXMLLoader.load(getClass().getResource("GeneratorKart.fxml")); ;
			 Main.scene = new Scene(layoutTalia,400,400);
			Main.windowLosuj.setScene(Main.scene);
			Main.windowLosuj.setTitle("Grywalizacja-O tworzenie talii");
			Main.windowLosuj.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	static public void goToStart(){
		VBox root;
		try {
			root = (VBox)FXMLLoader.load(StartController.class.getResource("Start.fxml"));
		 Main.scene =new Scene(root, 400, 400);
		 Main.windowLosuj.setScene(Main.scene);
			Main.windowLosuj.setTitle("Grywalizacja-start");
			Main.windowLosuj.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		Main.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
//		
//		primaryStage.show()
	}
	SampleController sampleController= new SampleController(); 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		sampleController.czytajDane();
		if(SampleController.czyRozpoczeta==0){
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
