package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Kontroler do losowania z metodą {@link #czytajDane()}
 * @author miro
 *
 */
public class SampleController implements Initializable {
	public static Talia talia = new Talia();
	/**
	 * 0 talia zero  ??
	 * 1 nie rozpoczeta
	 * 2 rozpoczeta
	 */
	public static int czyRozpoczeta;
	Connection conection;
	public Label labAbout =new Label("twórca Arkadiusz Mirosław");
	
	public Stage stageAbout = new Stage();
	@FXML
	public void about(ActionEvent e){
		System.out.println("okno about");
		
			Pane layoutAbout = new Pane();
			layoutAbout.getChildren().add(labAbout);
		Scene scene = new Scene(layoutAbout,400,200);
		stageAbout.setScene(scene);
		//blokowanie poprzedniego okna powoduje blad przy dwukrotnym wywolaniu
//		stageAbout.initModality(Modality.APPLICATION_MODAL);
		stageAbout.setTitle("Grywalizacja-O programie");
		stageAbout.showAndWait();
	}
	@FXML
	void scenaKarta(ActionEvent e) throws IOException{
		
			GridPane layoutTalia = (GridPane) FXMLLoader.load(getClass().getResource("GeneratorKart.fxml")); ;
		Scene sceneTalia = new Scene(layoutTalia,400,400);
		Main.windowLosuj.setScene(sceneTalia);
		Main.windowLosuj.setTitle("Grywalizacja-O tworzenie talii");
		Main.windowLosuj.show();
	}
	static String ileKart;
	private Image award1 = new Image("img/award1.png");
	private Image award2 = new Image("img/award2.png");
	private Image award3 = new Image("img/award3.png");
//	private Image award4 = new Image("http://thumbs.dreamstime.com/t/caw-25623361.jpg");
	private Image award4 = new Image("img/award3.png");
	@FXML
	private ImageView obrazek;
	@FXML
	private Button zamknij;
	@FXML
	private Button los;
	@FXML
	public Label wylosowane;
	@FXML
	private Label pozostalo;
	@FXML
	private Label pozostaloSrednich;
	@FXML
	private Label pozostaloMalych;

	/**
	 * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
	 * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
	 * 
	 * @param event
	 */
	@FXML
	void losuj(ActionEvent event) {
		if (talia.arrayTalia.isEmpty()) {
			czyRozpoczeta = 1;
			wylosowane.setText("gratuluję zakończyłeś talię");
		} else {
			czyRozpoczeta = 2;
			String teskt = talia.arrayTalia.get(0).toString();
			wylosowane.setText(teskt);
			talia.setIleKart(talia.getIleKart() - 1);
			if (talia.arrayTalia.get(0).getTyp() == 1) {
				obrazek.setImage(award1);
			}
			if (talia.arrayTalia.get(0).getTyp() == 2) {
				talia.setIleSrednich(talia.getIleSrednich() - 1);
				obrazek.setImage(award2);
			}
			if (talia.arrayTalia.get(0).getTyp() == 3) {
				talia.setIleMalych(talia.getIleMalych() - 1);
				obrazek.setImage(award3);
			}
			if (talia.arrayTalia.get(0).getTyp() == 4) {
				obrazek.setImage(award4);
			}
			pozostalo.setText("wszystkie");
			pozostalo.setText(Integer.toString(talia.getIleKart()));
			pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
			pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
			talia.arrayTalia.remove(0);
		}

	}

	/**
	 * usuwa dane z BD i zapisuje zmienne talii i karty do niej
	 */
	@FXML
	// void zapisz(ActionEvent event) // bo nie działało wywolywanie tej funkcji
	// w main
	void zapisz(ActionEvent event) {
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
			preStmt.setInt(1, talia.getIleKart());
			preStmt.setInt(2, talia.getIleMalych());
			preStmt.setInt(3, talia.getIleSrednich());
			preStmt.setInt(4, czyRozpoczeta);

			preStmt.executeUpdate();
			System.out.println("zapisane dane, czyRozpoczeta" + czyRozpoczeta);
			String sql = "";
			for (int i = 0; i < talia.arrayTalia.size(); i++) {

				query = "INSERT INTO karta VALUES (" + i + "," + talia.arrayTalia.get(i).getTyp() + ",'"
						+ talia.arrayTalia.get(i).getTytul() + "','" + talia.arrayTalia.get(i).getOpis() + "','"
						+ talia.arrayTalia.get(i).getObrazek() + "');";
				sql += query;
			}
			System.out.println(sql);
			System.out.println("ile kart w talii zapisz()"+talia.arrayTalia.size());
			mySta.executeUpdate(sql);

			conection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * czyta zmienne tali
	 * 
	 */
	public void czytajDane() {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from talia");
			while (rs.next()) {
				czyRozpoczeta = rs.getInt("czyRozpoczeta");
				talia.setNazwa(rs.getString("nazwa"));
				talia.setIleMalych(rs.getInt("ileMalych"));
				talia.setIleSrednich(rs.getInt("ileSrednich"));
				talia.setIleKart(rs.getInt("ileKart"));
			}
			System.out.println("nazwa talii "+talia.getNazwa());
			System.out.println("ile malych"+talia.getIleMalych());
			System.out.println("ile malych"+czyRozpoczeta);
			conection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * sprawdza czy takia została zaczęta czy nie, wywołuje odpowiednie metody i wyświtla ilość kart
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("przejście na scene z losowaniem");
			System.out.println("ile kart w talii zapisz()"+talia.arrayTalia.size());
		System.out.println("przed dane" + czyRozpoczeta);
		czytajDane();
			System.out.println("ile kart w talii czytajDane()"+talia.arrayTalia.size());
		System.out.println("po dane" + czyRozpoczeta);
		// nie dziala
		if(czyRozpoczeta==0){
//			try {
//				scenaKarta(null);
			System.out.println("talia zero");
				czyRozpoczeta=1;
//				zamknij.setDisable(true);
//				los.setDisable(true);
		talia.czytajTalie();   //gdy już jest talia zapisana w bd
//				talia.setIleSrednich(3);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else {
			System.out.println("odblokowanie buttonów");
				zamknij.setDisable(false);
				los.setDisable(false);
				los.disabledProperty();
		}
		// gdy jest utworzona
	
		obrazek.setImage(award4);
		if (czyRozpoczeta==2) {
			System.out.println("rozpoczeta");
		talia.czytajTalie();   //gdy już jest talia zapisana w bd
			System.out.println("ile kart w talii czytajTalie()"+talia.arrayTalia.size());
		} 
		// nie powinno być tego stanu przy pierwszym wczytaniu programu
		else if(czyRozpoczeta==1) {
			talia.setNazwa("nowa talia");
			talia.setIleKart(40);
			talia.setIleMalych(9);
			talia.setIleSrednich(3);
//			talia.tworzNagrody(); // jak nie wczytujemy kart z bazy danch
			talia.tworzTalie();
			wylosowane.setText("perzełącz scene");
		}
		pozostalo.setText(Integer.toString(talia.getIleKart()));
		pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
		pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
	}

}
