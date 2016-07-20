package view;

/**
		 * 	layout poczatkowy
		 * przelaczanie pomiedzy scenami 
		 * mozna dodac elementy menu
	
		 */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartController implements Initializable {
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnDraw;
	@FXML
	private Label info;

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private ViewManager manager;

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}

	@FXML
	private void goToDrawCard() {
		manager.showDrawCard();

	}

	@FXML
	private void goToCreate() {
		manager.showCardCreator();

	}

	@FXML
	private void goToStart() {
		manager.showStart();
	}

	DrawCardController sampleController = new DrawCardController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//
		sampleController.readDeckInfo();
		if (DrawCardController.isStarted == 0) {
			// scenaKarta(null);
			System.out.println("Start: takia nie rozpoczęta");
			btnDraw.setDisable(true);
		}
		// gdy jest utworzona
		else {
			System.out.println("rozpoczeta");
			// btnDraw.setDisable(false);
		}
	}

}
