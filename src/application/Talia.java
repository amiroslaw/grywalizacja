package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Zawiera metody {@link #czytajTalie()}, {@link #tworzNagrody()}, {@link #tworzTalie()}
 * {@link #arrayTalia} przechowuje objekty kart
 * 
 * @author miro
 *
 */
public class Talia {

	Connection conection;
	public ArrayList<Karta> arrayTalia = new ArrayList<Karta>();
	private int ileKart, ileMalych, ileSrednich;
	private String nazwa;

	public Talia() {
		// nazwa = "typowa talia";
		// ileKart = 40;
		// ileMalych = 6;
		// ileSrednich = 3;

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

	/**
	 * czyta dane z BD z tabeli karta
	 */
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
			// 
			e.printStackTrace();
		}
	}

	/**
	 * tworzy domyslne karty dla 3 kategorii
	 * 
	 */
	public void tworzNagrody() {
		// na razie najlepsza tworzona na poczatku
		arrayTalia.add(new Karta(1, "nagroda najlepsza"));
		for (int i = 0; i < ileSrednich; i++) {
			arrayTalia.add(new Karta(2, "nagroda nr2"));
		}
		for (int i = 0; i < ileMalych; i++) {
			arrayTalia.add(new Karta(3, "nagroda nr3"));
		}
		// for (int i = 0; i < ileKart-1-ileMalych-ileSrednich; i++) {
		// arrayTalia.add(new Karta());
		// }
	}

	/**
	 * dopełnia do istniejących kart z nagrodami karty puste i sortuje je
	 */
	public void tworzTalie() {
		// kopiowanie najlepszej nagrody do temp bo w bd jest na koncu
		Karta temp = arrayTalia.get(0);
		arrayTalia.remove(0);
		for (int i = 0; i < ileKart - 1 - ileMalych - ileSrednich; i++) {
			arrayTalia.add(new Karta());
		}
		Collections.shuffle(arrayTalia);
		arrayTalia.add(temp);
		System.out.println("ile kart czytaTalie"+arrayTalia.size());
	}

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
}
