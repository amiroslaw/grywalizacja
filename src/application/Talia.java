package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Talia {

	Connection conection;
	public ArrayList<Karta> arrayTalia = new ArrayList<Karta>();
	private int ileKart, ileMalych, ileSrednich;
	private String nazwa;

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public int getIleKart() {
		return ileKart;
	}

	public void setIleKart(int ileKart) {
		this.ileKart = ileKart;
	}

	public int getIleMalych() {
		return ileMalych;
	}

	public void setIleMalych(int ileMalych) {
		this.ileMalych = ileMalych;
	}

	public int getIleSrednich() {
		return ileSrednich;
	}

	public void setIleSrednich(int ileSrednich) {
		this.ileSrednich = ileSrednich;
	}

	public Talia() {
//		nazwa = "typowa talia";
//		ileKart = 40;
//		ileMalych = 9;
//		ileSrednich = 3;

	}

	public Talia(String nazwa) {
		this();
		this.nazwa = nazwa;
	}

	public Talia(int ileKart, int ileMalych, int ileSrednich, String nazwa) {
		this(nazwa);
		this.ileKart = ileKart;
		this.ileMalych = ileMalych;
		this.ileSrednich = ileSrednich;
	}

	public String toString() {
		return "nazwa: " + nazwa + "; \nile Kart: " + ileKart + "; \nile Malych: " + ileMalych + "; \nile Srednich: "
				+ ileSrednich;
	}

	public void czytajTalie() {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from karta");
			while (rs.next()) {
				arrayTalia.add(new Karta(rs.getInt("typ"), rs.getString("tytul"), rs.getString("opis"),
						rs.getString("obrazek")));
			}
			conection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tworzTalie() {
		// kopiowanie najlepszej nagrody do temp bo w bd jest na koncu
	Karta temp = arrayTalia.get(0);	
	arrayTalia.remove(0);
		for (int i = 0; i < ileKart - 1 - ileMalych - ileSrednich; i++) {
			arrayTalia.add(new Karta());
		}
		Collections.shuffle(arrayTalia);
		arrayTalia.add(temp);
	}

}

	// for (int i = 0; i < ileSrednich; i++) {
	// arrayTalia.add(new Karta(2,"nagroda nr2"));
	// }
	// for (int i = 0; i < ileMalych; i++) {
	// arrayTalia.add(new Karta(3,"nagroda nr3"));
	// }
	// for (int i = 0; i < ileKart-1-ileMalych-ileSrednich; i++) {
	// arrayTalia.add(new Karta());
	// }
	// arrayTalia.add(new Karta(1,"nagroda najlepsza"));