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

public class CardController implements Initializable {

	SampleController controller = new SampleController();
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
	String linkObrazek; 
	int iKarta = 1;
	@FXML
	public void otworzObrazek(ActionEvent e){
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
	linkObrazek=file.getAbsolutePath();
		}
		//nie zadzaila bo musi byc klikniety button i nie wybrany obrazek aby sie aktywowal else
//		else{ linkObrazek= "default"; }
	}
	@FXML
	public void zapisz(ActionEvent e){
		controller.zapisz(null);
	}
	/**
	 * zapisuje kartę do talii i odświerza scene
	 * @param e
	 */
	@FXML
	public void nastepnaKarta(ActionEvent e) {
		wyswietlTyp();
		Karta karta = new Karta();
		karta.setTytul(txtNazwa.getText());
		karta.setOpis(txtOpis.getText());
		if(file!=null){
		karta.setObrazek(linkObrazek);
		}else {
		karta.setObrazek("default");
		}
		file=null;
		// dodac metode czytajaca link do obrazka
		switch (iKarta) {
		case 1:
//			lblTyp.setText(Integer.toString(1));
			karta.setTyp(1);
			break;
		case 2:
		case 3:
		case 4:
			karta.setTyp(2);
			break;
		default:
//			lblTyp.setText(Integer.toString(3));
			karta.setTyp(3);
			break;
		}
		SampleController.talia.arrayTalia.add(karta);
		iKarta++;
			lblnrKarty.setText(Integer.toString(iKarta));
			txtNazwa.setText("");
//			if(file!=null)
			txtOpis.setText("");
		if(iKarta==11){
			btnDalej.setDisable(true);
			SampleController.czyRozpoczeta=1;
			SampleController.talia.setIleKart(40);
			SampleController.talia.setIleMalych(6);
			SampleController.talia.setIleSrednich(3);
			SampleController.talia.tworzTalie();
			controller.zapisz(null);
		}
			
	}
	/**
	 * ustawianie laber typ gdyz jest opuzniony najpierw wyswietla sie startowy stage
	 */
	public void wyswietlTyp(){
		if(iKarta>0 && iKarta<4)
			lblTyp.setText(Integer.toString(2));
		else
			lblTyp.setText(Integer.toString(3));
	}

	@FXML
	public void scenaLosuj(ActionEvent e) {
		// zmienione na przejscie na start
		controller.zapisz(null);
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
		System.out.println("czy rozpoczęta "+SampleController.czyRozpoczeta);
		SampleController.czyRozpoczeta = 0;
		SampleController.talia.arrayTalia.clear();
//		nastepnaKarta(null);
	}


}
