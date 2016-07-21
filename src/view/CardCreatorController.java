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

public class CardCreatorController implements Initializable {

	DrawCardController drawCardController = new DrawCardController();
	private ViewManager manager;
	private Stage primaryStage;

	// Talia talia = new Talia();
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
	/**
	 * typ karty
	 */
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
		// for (int i = 0; i < SampleController.talia.arrayTalia.size(); i++) {
		// System.out.println(SampleController.talia.arrayTalia.get(0));
		// }
		System.out.println("scena karta wczytanie");
		// trzeba to zapisac do bd bo przy wczytaniu tamtej sceny czyta z bd
		System.out.println("czy rozpoczęta " + DrawCardController.isStarted);
		DrawCardController.isStarted = 0;
		DrawCardController.deck.cardsList.clear();
		// nastepnaKarta(null);
	}

	@FXML
	void showStartWindow(ActionEvent e) {
		drawCardController.saveDB(null);
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

		DrawCardController.deck.cardsList.add(card);
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
		DrawCardController.isStarted = 1;
		DrawCardController.deck.setHowManyCards(40);
		DrawCardController.deck.setHowManySmallCards(6);
		DrawCardController.deck.setHowManyMediumCards(3);
		DrawCardController.deck.createDeck();
		drawCardController.saveDB(null);
	}
}
