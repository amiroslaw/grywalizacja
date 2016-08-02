package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DBmanager;

public class StartController implements Initializable {
//	DrawCardController sampleController = new DrawCardController();
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnDraw;
	@FXML
	private Label info;
	private Stage primaryStage;
	private ViewManager manager;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBmanager.readDeckInfo();
		if (DBmanager.deck.getIsStarted()==0) {
			System.out.println("Start: talia nie rozpoczęta");
//			btnDraw.setDisable(true);
			btnDraw.setVisible(false);
		}
		// gdy jest utworzona
		else {
			System.out.println("rozpoczeta");
		}
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
}
