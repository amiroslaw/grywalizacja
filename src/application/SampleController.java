package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
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
	public static int czyRozpoczeta=0;
	Connection conection;
	public Label labAbout =new Label("twórca Arkadiusz Mirosław");
	static String ileKart;
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
	public Label wylosowane;
	@FXML
	private Label pozostalo;
	@FXML
	private Label pozostaloSrednich;
	@FXML
	private Label pozostaloMalych;

	public Stage stageAbout = new Stage();
	File f;	
	/**
	 * tworzenie stage o mnie 
	 */
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
	/**
	 * przełączenie na scene z tworzeniem talii
	 * @param e
	 * @throws IOException
	 */
	@FXML
	void scenaKarta(ActionEvent e) throws IOException{
//zmienione na przejdz do startu
		StartController.goToStart();
//			GridPane layoutTalia = (GridPane) FXMLLoader.load(getClass().getResource("GeneratorKart.fxml")); ;
//		Scene sceneTalia = new Scene(layoutTalia,400,400);
//		Main.windowLosuj.setScene(sceneTalia);
//		Main.windowLosuj.setTitle("Grywalizacja-O tworzenie talii");
//		Main.windowLosuj.show();
	}

	/**
	 * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
	 * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
	 * 
	 * @param event
	 */
	@FXML
	void losuj(ActionEvent event) {
//		if (talia.arrayTalia.isEmpty()) {
		if (talia.arrayTalia.size()==1) {
			czyRozpoczeta = 0;
			zapisz(null);
			menuStart.setDisable(false);
			wylosowane.setText("gratuluję zakończyłeś talię");
		} else {
			
			czyRozpoczeta = 1;
			String teskt = talia.arrayTalia.get(0).toString();
			wylosowane.setText(teskt);
			talia.setIleKart(talia.getIleKart() - 1);
			if (talia.arrayTalia.get(0).getTyp() == 1) {
				if(talia.arrayTalia.get(0).getObrazek().equals("default")){
				obrazek.setImage(award1);
				}
				else{
					f = new File(talia.arrayTalia.get(0).getObrazek());
				obrazek.setImage( new Image(f.toURI().toString()));
				}
			}
			if (talia.arrayTalia.get(0).getTyp() == 2) {
				talia.setIleSrednich(talia.getIleSrednich() - 1);
				if(talia.arrayTalia.get(0).getObrazek().equals("default")){
				obrazek.setImage(award2);
				}
				else{
					f = new File(talia.arrayTalia.get(0).getObrazek());
				obrazek.setImage( new Image(f.toURI().toString()));
				}
			}

			if (talia.arrayTalia.get(0).getTyp() == 3) {
				talia.setIleMalych(talia.getIleMalych() - 1);
				if(talia.arrayTalia.get(0).getObrazek().equals("default")){
				obrazek.setImage(award3);
				}
				else{
					f = new File(talia.arrayTalia.get(0).getObrazek());
				obrazek.setImage( new Image(f.toURI().toString()));
				}
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
	 * czyta zmienne tali z bazy danych
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
		menuStart.setDisable(true);
		czytajDane();
		talia.arrayTalia.clear();
		talia.czytajTalie();   //gdy już jest talia zapisana w bd
		obrazek.setImage(award4);
		System.out.println("przejście na scene z losowaniem");
			System.out.println("ile kart w talii zapisz()"+talia.arrayTalia.size());
		System.out.println("przed dane" + czyRozpoczeta);
			System.out.println("ile kart w talii czytajDane()"+talia.arrayTalia.size());
		System.out.println("po dane" + czyRozpoczeta);
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
////				// TODO Auto-generated catch block
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
		pozostalo.setText(Integer.toString(talia.getIleKart()));
		pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
		pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
	}

}
