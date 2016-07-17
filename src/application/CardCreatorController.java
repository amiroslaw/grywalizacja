package application;

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

public class CardCreatorController implements Initializable {

	DrawCardController drawCardController = new DrawCardController();
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
	File file;
	String linkToImage; 
	int cardCounter = 1;
	@FXML
	public void chooseImage(ActionEvent e){
		FileChooser fileChooser= new FileChooser();
		fileChooser.setTitle("wybierz obrazek");
		fileChooser.setInitialDirectory(new File (System.getProperty("user.home")) );
//		fileChooser.getExtensionFilters().addAll(
//				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//				new FileChooser.ExtensionFilter("All file", "*.*")
//				);
//	File file = new File(null);
		file = fileChooser.showOpenDialog(new Stage());
		if(file!=null){
			// przypisanie ścieżki lub i skpiowanie
			System.out.println(file.getAbsolutePath());
//		karta.setObrazek(file.getAbsolutePath());
	linkToImage=file.getAbsolutePath();
		}
		//nie zadzaila bo musi byc klikniety button i nie wybrany obrazek aby sie aktywowal else
//		else{ linkObrazek= "default"; }
	}
	@FXML
	public void saveDB(ActionEvent e){
		drawCardController.saveDB(null);
	}
	/**
	 * zapisuje kartę do talii i odświerza scene
	 * @param e
	 */
	@FXML
	public void nextCard(ActionEvent e) {
		viewType();
		Card card = new Card();
		card.setTitle(txtNazwa.getText());
		card.setDescription(txtOpis.getText());
		if(file!=null){
		card.setImage(linkToImage);
		}else {
		card.setImage("default");
		}
		file=null;
		// dodac metode czytajaca link do obrazka
		switch (cardCounter) {
		case 1:
//			lblTyp.setText(Integer.toString(1));
			card.setType(1);
			break;
		case 2:
		case 3:
		case 4:
			card.setType(2);
			break;
		default:
//			lblTyp.setText(Integer.toString(3));
			card.setType(3);
			break;
		}
		DrawCardController.deck.cardsList.add(card);
		cardCounter++;
			lblnrKarty.setText(Integer.toString(cardCounter));
			txtNazwa.setText("");
//			if(file!=null)
			txtOpis.setText("");
		if(cardCounter==11){
			btnDalej.setDisable(true);
			DrawCardController.isStarted=1;
			DrawCardController.deck.setHowManyCards(40);
			DrawCardController.deck.setHowManySmallCards(6);
			DrawCardController.deck.setHowManyMediumCards(3);
			DrawCardController.deck.createDeck();
			drawCardController.saveDB(null);
		}
			
	}
	/**
	 * ustawianie laber typ gdyz jest opuzniony najpierw wyswietla sie startowy stage
	 */
	public void viewType(){
		if(cardCounter>0 && cardCounter<4)
			lblTyp.setText(Integer.toString(2));
		else
			lblTyp.setText(Integer.toString(3));
	}

	@FXML
	public void showDrawCard(ActionEvent e) {
		// zmienione na przejscie na start
		drawCardController.saveDB(null);
		StartController.goToStart();
		try {
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
//			Main.windowLosuj.setScene(Main.scene);
//			Main.windowLosuj.setTitle("Grywalizacja- losuj");
//			Main.windowLosuj.setOnCloseRequest(event->controller.zapisz(null));
//			Main.windowLosuj.show();

		} catch (Exception ev) {
			ev.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		for (int i = 0; i < SampleController.talia.arrayTalia.size(); i++) {
//			System.out.println(SampleController.talia.arrayTalia.get(0));
//		}
		System.out.println("scena karta wczytanie");
		// trzeba to zapisac do bd bo przy wczytaniu tamtej sceny czyta z bd
		System.out.println("czy rozpoczęta "+DrawCardController.isStarted);
		DrawCardController.isStarted = 0;
		DrawCardController.deck.cardsList.clear();
//		nastepnaKarta(null);
	}


}
