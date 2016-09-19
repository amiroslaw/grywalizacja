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
	@FXML
	private Button btnPreviousCard;
	
	private Card card;

	private File fileChooserImage;
	private String linkToImage;
	private int cardCounter = 1;

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnPreviousCard.setDisable(true);
		DBmanager.deck.setIsStarted(0);
		DBmanager.deck.cardsList.clear();
	}

	@FXML
	void showStartWindow(ActionEvent e) {
		DBmanager.saveDB();
		manager.showStart();
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
		fileChooserImage = fileChooser.showOpenDialog(new Stage());
		if (fileChooserImage != null) {
			linkToImage = fileChooserImage.getAbsolutePath();
		}
	}

	@FXML
	void saveDB(ActionEvent e) {
		DBmanager.saveDB();
	}


	/**
	 * zapisuje kartę do talii i odświerza scene
	 */
	@FXML
	private void nextCard(ActionEvent e) {
		btnPreviousCard.setDisable(false);
		card = new Card();
		viewTypeOfAward();

		setCardProperties();

		DBmanager.deck.cardsList.add(card);
		cardCounter++;

		lblnrKarty.setText(Integer.toString(cardCounter));

		fileChooserImage = null;
		// TODO: odkomentowac
		// txtNazwa.setText("");
		// txtOpis.setText("");

		if (cardCounter == 11) {
			//TODO: nazwę talii i zapis talii z samymi nagrodami do pozniejszej edycji i zapisu/wyboru
			primaryStage.close();
			manager.showDeckNameDialog();
		manager.showStart();
//			endEdition();
		}
	}
	@FXML
	private void previousCard(){
		if(cardCounter==2){
			btnPreviousCard.setDisable(true);
		}
		cardCounter--;
		DBmanager.deck.cardsList.remove(cardCounter-1);
//		Card tempCard = new Card();
		Card tempCard = DBmanager.deck.cardsList.get(DBmanager.deck.cardsList.size()-1);
		txtNazwa.setText(tempCard.getTitle());
		txtOpis.setText(tempCard.getDescription());
		lblnrKarty.setText(Integer.toString(cardCounter));
		viewTypeOfAward();
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
		if (fileChooserImage != null) {
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
		// przeniesione do deckNameDialog
//		btnDalej.setDisable(true);
//		DBmanager.deck.setIsStarted(1);
//		DBmanager.deck.setHowManyCards(40);
//		DBmanager.deck.setHowManySmallCards(6);
//		DBmanager.deck.setHowManyMediumCards(3);
//// TODO: przeniesc gdzies do wybierania decku
////		 DBmanager.deck.createDeck();
//		DBmanager.saveDB();
//		manager.showStart();
//		DBmanager.showDeck();
	}

}
