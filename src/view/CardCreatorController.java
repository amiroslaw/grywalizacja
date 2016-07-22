package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Card;
import model.DBmanager;

public class CardCreatorController implements Initializable {

	DrawCardController drawCardController = new DrawCardController();
	private ViewManager manager;
	private Stage primaryStage;

	@FXML
	private Label lblnrKarty;
	@FXML
	private Label lblTyp;
	@FXML
	private TextField txtNazwa;
	@FXML
	private TextArea txtOpis;
	@FXML
	private Button btnObrazek;
	@FXML
	private Button btnDalej;

	private File file;
	private String linkToImage;
	private int cardCounter = 1;
	private Card card = new Card();

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DBmanager.deck.setIsStarted(0);
		DBmanager.deck.cardsList.clear();
	}

	@FXML
	void showStartWindow(ActionEvent e) {
		DBmanager.saveDB();
		manager.showDrawCard();
	}

	@FXML
	void chooseImage(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("wybierz obrazek");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		// fileChooser.getExtensionFilters().addAll(
		// new FileChooser.ExtensionFilter("JPG", "*.jpg"),
		// new FileChooser.ExtensionFilter("All file", "*.*")
		// );
		// File file = new File(null);
		file = fileChooser.showOpenDialog(new Stage());
		if (file != null) {
			linkToImage = file.getAbsolutePath();
		}
	}

	@FXML
	void saveDB(ActionEvent e) {
		drawCardController.saveDB(null);
	}

	/**
	 * zapisuje kartę do talii i odświerza scene
	 * 
	 * @param e
	 */
	@FXML
	void nextCard(ActionEvent e) {
		viewTypeOfAward();

		setCardProperties();

		DBmanager.deck.cardsList.add(card);
		cardCounter++;

		lblnrKarty.setText(Integer.toString(cardCounter));

		file = null;
		txtNazwa.setText("");
		txtOpis.setText("");

		if (cardCounter == 11) {
			endEdition();
		}
	}

	private void viewTypeOfAward() {
		if (cardCounter > 0 && cardCounter < 4)
			lblTyp.setText(Integer.toString(2));
		else
			lblTyp.setText(Integer.toString(3));
	}

	private void setCardProperties() {
		card.setTitle(txtNazwa.getText());
		card.setDescription(txtOpis.getText());
		if (file != null) {
			card.setImage(linkToImage);
		} else {
			card.setImage("default");
		}
		// TODO: dodac metode czytajaca link do obrazka
		switch (cardCounter) {
		case 1:
			card.setType(1);
			break;
		case 2:
		case 3:
		case 4:
			card.setType(2);
			break;
		default:
			card.setType(3);
			break;
		}
	}

	private void endEdition() {
		btnDalej.setDisable(true);
		DBmanager.deck.setIsStarted(1);
		DBmanager.deck.setHowManyCards(40);
		DBmanager.deck.setHowManySmallCards(6);
		DBmanager.deck.setHowManyMediumCards(3);
		DBmanager.deck.createDeck();
		DBmanager.saveDB();
	}
}
