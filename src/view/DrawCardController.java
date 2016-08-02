package view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DBmanager;
import model.Deck;
import model.SqliteConnection;

/**
 * Kontroler do losowania z metodą {@link #readDeckInfo()}
 * 
 * @author miro
 *
 */
public class DrawCardController implements Initializable {

	
	private ViewManager manager;
	private Stage primaryStage;
	private Label labAbout = new Label("twórca Arkadiusz Mirosław");
	private Image award1 = new Image("img/award1.png");
	private Image award2 = new Image("img/award2.png");
	private Image award3 = new Image("img/award3.png");
	private Image award4 = new Image("img/cow.png");
	// private Image award4 = new Image("file:/home/miro/img/dum.jpg");
	// private Image award4 = new Image("file:/home/img/cpgoesdqxww.jpg");
	@FXML
	private MenuItem menuStart;
	@FXML
	private ImageView obrazek;
	@FXML
	private Button zamknij;
	@FXML
	private Button los;
	@FXML
	private Label wylosowane;
	@FXML
	private Label pozostalo;
	@FXML
	private Label pozostaloSrednich;
	@FXML
	private Label pozostaloMalych;

	private File imageFile;

	public void setManager(ViewManager manager) {
		this.manager = manager;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * sprawdza czy takia została zaczęta czy nie, wywołuje odpowiednie metody i
	 * wyświtla ilość kart
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuStart.setDisable(true);
		DBmanager.readDeckInfo();
		DBmanager.deck.cardsList.clear();
		DBmanager.readCards(); 
		obrazek.setImage(award4);

		pozostalo.setText(Integer.toString(DBmanager.deck.getHowManyCards()));
		pozostaloSrednich.setText(Integer.toString(DBmanager.deck.getHowManyMediumCards()));
		pozostaloMalych.setText(Integer.toString(DBmanager.deck.getHowManySmallCards()));
	}

	@FXML
	void showAbout(ActionEvent e) {
		manager.showAbout();
	}

	@FXML
	void showStart(ActionEvent e) {
		manager.showStart();

	}

	@FXML
	void saveDB(ActionEvent event) {
		DBmanager.saveDB();
	}

	/**
	 * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
	 * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
	 */
	@FXML
	void drawCard(ActionEvent event) {
		if (DBmanager.deck.cardsList.size() == 0) {
			DBmanager.deck.setIsStarted(0);
			DBmanager.saveDB();
			menuStart.setDisable(false);
			wylosowane.setText("gratuluję zakończyłeś talię");
		} else {
			DBmanager.deck.setIsStarted(1);
			String teskt = DBmanager.deck.cardsList.get(0).toString();
			wylosowane.setText(teskt);
			DBmanager.deck.setHowManyCards(DBmanager.deck.getHowManyCards() - 1);
			checkTypeAndSetImage();

			pozostalo.setText(Integer.toString(DBmanager.deck.getHowManyCards()));
			pozostaloSrednich.setText(Integer.toString(DBmanager.deck.getHowManyMediumCards()));
			pozostaloMalych.setText(Integer.toString(DBmanager.deck.getHowManySmallCards()));

			DBmanager.deck.cardsList.remove(0);
		}

	}

	private void checkTypeAndSetImage() {
		int typeOfCard = DBmanager.deck.cardsList.get(0).getType();
		switch (typeOfCard) {
		case 1:
			if (DBmanager.deck.cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award1);
			} else {
				imageFile = new File(DBmanager.deck.cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		case 2:
			DBmanager.deck.setHowManyMediumCards(DBmanager.deck.getHowManyMediumCards() - 1);
			if (DBmanager.deck.cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award2);
			} else {
				imageFile = new File(DBmanager.deck.cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		case 3:
			DBmanager.deck.setHowManySmallCards(DBmanager.deck.getHowManySmallCards() - 1);
			if (DBmanager.deck.cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award3);
			} else {
				imageFile = new File(DBmanager.deck.cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		default:
			obrazek.setImage(award4);
			break;
		}
	}

}
