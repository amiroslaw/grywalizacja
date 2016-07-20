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
import model.Deck;
import model.SqliteConnection;

/**
 * Kontroler do losowania z metodą {@link #readDeckInfo()}
 * @author miro
 *
 */
public class DrawCardController implements Initializable {
	private ViewManager manager;
	private Stage primaryStage;
	
	public void setManager(ViewManager manager) {
		this.manager = manager;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	public static Deck deck = new Deck();
	/**
	 * 0 talia zero  ??
	 * 1 nie rozpoczeta
	 * 2 rozpoczeta
	 */
	public static int isStarted=0;
	Connection conection;
	private Label labAbout =new Label("twórca Arkadiusz Mirosław");
//	static String ileKart;
	private Image award1 = new Image("img/award1.png");
	private Image award2 = new Image("img/award2.png");
	private Image award3 = new Image("img/award3.png");
	private Image award4 = new Image("img/cow.png");
//	private Image award4 = new Image("file:/home/miro/img/dum.jpg");
//	private Image award4 = new Image("file:/home/img/cpgoesdqxww.jpg");
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

	File file;	

	@FXML
	public void showAbout(ActionEvent e){
		manager.showAbout();
	}

	@FXML
	void showStart(ActionEvent e) {
		manager.showStart();

	}

	/**
	 * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
	 * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
	 * 
	 * @param event
	 */
	@FXML
	void drawCard(ActionEvent event) {
//		if (talia.arrayTalia.isEmpty()) {
		if (deck.cardsList.size()==0) {
			isStarted = 0;
			saveDB(null);
			menuStart.setDisable(false);
			wylosowane.setText("gratuluję zakończyłeś talię");
		} else {
			
			isStarted = 1;
			String teskt = deck.cardsList.get(0).toString();
			wylosowane.setText(teskt);
			deck.setHowManyCards(deck.getHowManyCards() - 1);
			if (deck.cardsList.get(0).getType() == 1) {
				if(deck.cardsList.get(0).getImage().equals("default")){
				obrazek.setImage(award1);
				}
				else{
					file = new File(deck.cardsList.get(0).getImage());
				obrazek.setImage( new Image(file.toURI().toString()));
				}
			}
			if (deck.cardsList.get(0).getType() == 2) {
				deck.setHowManyMediumCards(deck.getHowManyMediumCards() - 1);
				if(deck.cardsList.get(0).getImage().equals("default")){
				obrazek.setImage(award2);
				}
				else{
					file = new File(deck.cardsList.get(0).getImage());
				obrazek.setImage( new Image(file.toURI().toString()));
				}
			}

			if (deck.cardsList.get(0).getType() == 3) {
				deck.setHowManySmallCards(deck.getHowManySmallCards() - 1);
				if(deck.cardsList.get(0).getImage().equals("default")){
				obrazek.setImage(award3);
				}
				else{
					file = new File(deck.cardsList.get(0).getImage());
				obrazek.setImage( new Image(file.toURI().toString()));
				}
			}

			if (deck.cardsList.get(0).getType() == 4) {
				obrazek.setImage(award4);
			}
			pozostalo.setText("wszystkie");
			pozostalo.setText(Integer.toString(deck.getHowManyCards()));
			pozostaloSrednich.setText(Integer.toString(deck.getHowManyMediumCards()));
			pozostaloMalych.setText(Integer.toString(deck.getHowManySmallCards()));
			deck.cardsList.remove(0);
		}

	}

	/**
	 * usuwa dane z BD i zapisuje zmienne talii i karty do niej
	 */
	@FXML
	// void zapisz(ActionEvent event) // bo nie działało wywolywanie tej funkcji
	// w main
	void saveDB(ActionEvent event) {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			// usuwanie kart i tali
			mySta.executeUpdate("DELETE FROM talia WHERE id=1");
			mySta.executeUpdate("DELETE  FROM karta");
			// zapisywanie zmiennych z talii
			String query = "INSERT INTO talia VALUES (1, 'update talia', ?, ?, ?, ?)";
			PreparedStatement preStmt = conection.prepareStatement(query);
			preStmt.setInt(1, deck.getHowManyCards());
			preStmt.setInt(2, deck.getHowManySmallCards());
			preStmt.setInt(3, deck.getHowManyMediumCards());
			preStmt.setInt(4, isStarted);

			preStmt.executeUpdate();
			System.out.println("zapisane dane, czyRozpoczeta" + isStarted);
			String sql = "";
			for (int i = 0; i < deck.cardsList.size(); i++) {

				query = "INSERT INTO karta VALUES (" + i + "," + deck.cardsList.get(i).getType() + ",'"
						+ deck.cardsList.get(i).getTitle() + "','" + deck.cardsList.get(i).getDescription() + "','"
						+ deck.cardsList.get(i).getImage() + "');";
				sql += query;
			}
			System.out.println(sql);
			System.out.println("ile kart w talii zapisz()"+deck.cardsList.size());
			mySta.executeUpdate(sql);

			conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * czyta zmienne tali z bazy danych
	 * 
	 */
	public void readDeckInfo() {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from talia");
			while (rs.next()) {
				isStarted = rs.getInt("czyRozpoczeta");
				deck.setName(rs.getString("nazwa"));
				deck.setHowManySmallCards(rs.getInt("ileMalych"));
				deck.setHowManyMediumCards(rs.getInt("ileSrednich"));
				deck.setHowManyCards(rs.getInt("ileKart"));
			}
			System.out.println("nazwa talii "+deck.getName());
			System.out.println("ile malych"+deck.getHowManySmallCards());
			System.out.println("ile malych"+isStarted);
			conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * sprawdza czy takia została zaczęta czy nie, wywołuje odpowiednie metody i wyświtla ilość kart
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuStart.setDisable(true);
		readDeckInfo();
		deck.cardsList.clear();
		deck.readCards();   //gdy już jest talia zapisana w bd
		obrazek.setImage(award4);
		System.out.println("przejście na scene z losowaniem");
			System.out.println("ile kart w talii zapisz()"+deck.cardsList.size());
		System.out.println("przed dane" + isStarted);
			System.out.println("ile kart w talii czytajDane()"+deck.cardsList.size());
		System.out.println("po dane" + isStarted);
		// dodane nr.2 aby nie musiec zmieniac w bd
		// nie dziala
//		if(czyRozpoczeta==0){
////			try {
////				scenaKarta(null);
//			System.out.println("talia zero");
//				czyRozpoczeta=1;
////				zamknij.setDisable(true);
////				los.setDisable(true);
//		talia.czytajTalie();   //gdy już jest talia zapisana w bd
////				talia.setIleSrednich(3);
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
//		}else {
//			System.out.println("odblokowanie buttonów");
//				zamknij.setDisable(false);
//				los.setDisable(false);
//				los.disabledProperty();
//		}
//		// gdy jest utworzona
//	
//		if (czyRozpoczeta==1) {
//			System.out.println("rozpoczeta");
//		talia.czytajTalie();   //gdy już jest talia zapisana w bd
//			System.out.println("ile kart w talii czytajTalie()"+talia.arrayTalia.size());
//		} 
//		// nie powinno być tego stanu przy pierwszym wczytaniu programu
//		else if(czyRozpoczeta==0) {
//			talia.setNazwa("nowa talia");
//			talia.setIleKart(40);
//			talia.setIleMalych(6);
//			talia.setIleSrednich(3);
////			talia.tworzNagrody(); // jak nie wczytujemy kart z bazy danch
//			talia.tworzTalie();
//			wylosowane.setText("perzełącz scene");
//		}
		pozostalo.setText(Integer.toString(deck.getHowManyCards()));
		pozostaloSrednich.setText(Integer.toString(deck.getHowManyMediumCards()));
		pozostaloMalych.setText(Integer.toString(deck.getHowManySmallCards()));
	}

}
