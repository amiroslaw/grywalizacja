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
import model.Instrument;
import model.SqliteConnection;

/**
 * Kontroler do losowania z metodą {@link #readDeckInfo()}
 * 
 * @author miro
 *
 */
public class DrawCardController  {

	
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
	private String deckName;
	private DBmanager dataBase;
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
	    dataBase.saveDB();
	}

	/**
	 * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
	 * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
	 */
	@FXML
	void drawCard(ActionEvent event) {
		if (dataBase.getDeck().cardsList.size() == 0) {
		    dataBase.getDeck().setIsStarted(3);
		    dataBase.getMapOfDecks().remove(deckName);
//			DBmanager.saveDB();
			menuStart.setDisable(false);
			wylosowane.setText("gratuluję zakończyłeś talię");
		} else {
		    dataBase.getDeck().setIsStarted(1);
			String teskt = dataBase.getDeck().cardsList.get(0).toString();
			wylosowane.setText(teskt);
			dataBase.getDeck().setHowManyCards(dataBase.getDeck().getHowManyCards() - 1);
			checkTypeAndSetImage();

			pozostalo.setText(Integer.toString(dataBase.getDeck().getHowManyCards()));
			pozostaloSrednich.setText(Integer.toString(dataBase.getDeck().getHowManyMediumCards()));
			pozostaloMalych.setText(Integer.toString(dataBase.getDeck().getHowManySmallCards()));

			dataBase.getDeck().cardsList.remove(0);
		}

	}

	private void checkTypeAndSetImage() {
		int typeOfCard = dataBase.getDeck().cardsList.get(0).getType();
		switch (typeOfCard) {
		case 1:
			if (dataBase.getDeck().cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award1);
			} else {
				imageFile = new File(dataBase.getDeck().cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		case 2:
		    dataBase.getDeck().setHowManyMediumCards(dataBase.getDeck().getHowManyMediumCards() - 1);
			if (dataBase.getDeck().cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award2);
			} else {
				imageFile = new File(dataBase.getDeck().cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		case 3:
		    dataBase.getDeck().setHowManySmallCards(dataBase.getDeck().getHowManySmallCards() - 1);
			if (dataBase.getDeck().cardsList.get(0).getImage().equals("default")) {
				obrazek.setImage(award3);
			} else {
				imageFile = new File(dataBase.getDeck().cardsList.get(0).getImage());
				obrazek.setImage(new Image(imageFile.toURI().toString()));
			}
			break;
		default:
			obrazek.setImage(award4);
			break;
		}
	}

	public void init(String deckName, DBmanager dataBase) {
		this.dataBase = dataBase;
		this.deckName = deckName; 
		
		System.out.println("deckName in DrawCard");
		
		menuStart.setDisable(true);
		dataBase.readDeckInfo(deckName);
		dataBase.getDeck().cardsList.clear();
		dataBase.readCards(dataBase.getMapOfDecks().get(deckName)); 
		obrazek.setImage(award4);

		pozostalo.setText(Integer.toString(dataBase.getDeck().getHowManyCards()));
		pozostaloSrednich.setText(Integer.toString(dataBase.getDeck().getHowManyMediumCards()));
		pozostaloMalych.setText(Integer.toString(dataBase.getDeck().getHowManySmallCards()));
		
	}

}
