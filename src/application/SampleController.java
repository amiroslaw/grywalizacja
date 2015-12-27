package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SampleController implements Initializable{
	public static Talia talia = new Talia();
	public static boolean czyRozpoczeta ;
Connection  conection; 
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
    private Button zamknij;
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
    	    	if (talia.arrayTalia.isEmpty())
    	    	{
    	    		czyRozpoczeta= false;
    	    		wylosowane.setText("gratuluję zakończyłeś talię");
    	    	}else {
    	    		czyRozpoczeta= true;
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
    	    		
    }
    @FXML
void zapisz(ActionEvent event)
{
	conection = (Connection) SqliteConnection.Connector();
	if (conection == null) {

		System.out.println("connection not successful");
		System.exit(1);
	}
	try {
		Statement mySta = conection.createStatement();
		mySta.executeUpdate("DELETE FROM talia WHERE id=1");
		String query = "INSERT INTO talia VALUES (1, 'update talia', ?, ?, ?, ?)"; 
		PreparedStatement preStmt = conection.prepareStatement(query);
		preStmt.setInt(1, talia.getIleKart());
		preStmt.setInt(2, talia.getIleMalych());
		preStmt.setInt(3, talia.getIleSrednich());
		preStmt.setBoolean(4, czyRozpoczeta);
		
		preStmt.executeUpdate();
		System.out.println("zapisane dane, czyRozpoczeta" + czyRozpoczeta);
		System.out.println(Boolean.toString(czyRozpoczeta));
//		ResultSet rs = mySta.executeQuery("select * from talia");
//		while (rs.next()) {
//			czyRozpoczeta= rs.getBoolean("czyRozpoczeta");
//			talia.setNazwa(rs.getString("nazwa"));
//			talia.setIleMalych(rs.getInt("ileMalych"));
//			talia.setIleSrednich(rs.getInt("ileSrednich"));
//			talia.setIleKart(rs.getInt("ileKart"));
//		}
		conection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public  void czytajDane() {
	conection = (Connection) SqliteConnection.Connector();
	if (conection == null) {

		System.out.println("connection not successful");
		System.exit(1);
	}
	try {
		Statement mySta = conection.createStatement();
		ResultSet rs = mySta.executeQuery("select * from talia");
		while (rs.next()) {
			czyRozpoczeta= rs.getBoolean("czyRozpoczeta");
			talia.setNazwa(rs.getString("nazwa"));
			talia.setIleMalych(rs.getInt("ileMalych"));
			talia.setIleSrednich(rs.getInt("ileSrednich"));
			talia.setIleKart(rs.getInt("ileKart"));
		}
		conection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	System.out.println("przed dane"+ czyRozpoczeta);
    	czytajDane();
	System.out.println("po dane"+ czyRozpoczeta);
        talia.czytajTalie();
    	obrazek.setImage(award4);
        System.out.println("View is now loaded!");
        if(czyRozpoczeta)
        {
        	System.out.println("rozpoczeta");
        }else {
        talia.setNazwa("nowa talia");
        talia.setIleKart(40);
        talia.setIleMalych(9);
        talia.setIleSrednich(3);
    	talia.tworzTalie();
		}
    	pozostalo.setText(Integer.toString(talia.getIleKart()));
    	pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
    	pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
    }
public static void wyswietlIleKart() {
	System.out.println("kontroler");
	// nie udalo sie uwyswitlic przez zrobienie objektu tej klasy, tyrzeba byloby robic statyczna
//    	pozostalo.setText(Integer.toString(talia.getIleKart()));
//    	pozostaloSrednich.setText(Integer.toString(talia.getIleSrednich()));
//    	pozostaloMalych.setText(Integer.toString(talia.getIleMalych()));
}
}
