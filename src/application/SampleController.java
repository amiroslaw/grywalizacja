package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable{
	public Talia talia = new Talia();
//	public void ustaw(){
//talia.tworzTalie();
//	}
	
	static String ileKart;
    private Image award1= new Image("img/award1.png"); 
    private Image award2= new Image("img/award2.png"); 
    private Image award3= new Image("img/award3.png"); 
    private Image award4= new Image("http://thumbs.dreamstime.com/t/caw-25623361.jpg"); 
    @FXML
    private ImageView obrazek;
    @FXML
    private Button los;
    @FXML
   private Label wylosowane;
    @FXML
   private  Label  pozostalo;
    @FXML
   private  Label  pozostaloSrednich;
    @FXML
   private  Label  pozostaloMalych;
    @FXML
    void losuj(ActionEvent event) {
    	String teskt = talia.arrayTalia.get(0).toString();
    			wylosowane.setText(teskt);
    			talia.setIleKart(talia.getIleKart()-1);
    			if(talia.arrayTalia.get(0).getTyp()==1){
				obrazek.setImage(award1);
    			}
    			if(talia.arrayTalia.get(0).getTyp()==2){
    			talia.setIleSrednich(talia.getIleSrednich()-1);
				obrazek.setImage(award2);
    			}
    			if(talia.arrayTalia.get(0).getTyp()==3){
    			talia.setIleMalych(talia.getIleMalych()-1);
				obrazek.setImage(award3);
    			}
    			if(talia.arrayTalia.get(0).getTyp()==4){
				obrazek.setImage(award4);
    			}
				pozostalo.setText("wszystkie");
    	    	pozostalo.setText(Integer.toString(talia.getIleKart()));
    	    	pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
    	    	pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
    			talia.arrayTalia.remove(0);
    }
public static void wyswietlIleKart() {
	System.out.println("kontroler");
	// nie udalo sie uwyswitlic przez zrobienie objektu tej klasy, tyrzeba byloby robic statyczna
//    	pozostalo.setText(Integer.toString(talia.getIleKart()));
//    	pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
//    	pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
}
    	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	obrazek.setImage(award4);
        System.out.println("View is now loaded!");
        talia.czytajTalie();
    	talia.tworzTalie();
    	pozostalo.setText(Integer.toString(talia.getIleKart()));
    	pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
    	pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
    }
}
