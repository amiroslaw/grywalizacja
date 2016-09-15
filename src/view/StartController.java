package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DBmanager;

public class StartController implements Initializable {
//	DrawCardController sampleController = new DrawCardController();
	@FXML
	private Button btnCreate;
	@FXML
	private ComboBox<String> comboBoxOfDecks;
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
//		DBmanager.readDeckInfo();
//		if (DBmanager.deck.getIsStarted()==0) {
		if (DBmanager.amountOfDecks==0) {
			System.out.println("Start: jest jakas talia");
			btnDraw.setDisable(true);
//			btnDraw.setVisible(false);
		}
		// gdy jest utworzona
		else {
			createComboBox();
			System.out.println("rozpoczeta");
		}
	}
	public void createComboBox() {
		ObservableList<String> observableList = FXCollections.observableArrayList();
		for (int i = 0; i < DBmanager.listOfDecks.length; i++) {
			observableList.add(DBmanager.listOfDecks[i]);
		}
		comboBoxOfDecks.setItems(observableList);
		//TODO: problem z powtarzajacymi sie nazwami
//		ObservableMapValue observableMap =  (ObservableMapValue) FXCollections.observableHashMap();
//		for (int i = 0; i < DBmanager.listOfDecks.length; i++) {
//			observableMap.put("key", DBmanager.listOfDecks[i]);
//		}
//		comboBoxOfDecks.setItem(observableMap.get());
	}
	@FXML
	private void goToDrawCard() {
		manager.showDrawCard(comboBoxOfDecks.getValue());
//		System.out.println(comboBoxOfDecks.getValue());

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
