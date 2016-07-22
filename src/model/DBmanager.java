package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {
	public static Deck deck = new Deck();
	static Connection conection;

	public static void readDeckInfo() {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from talia");
			while (rs.next()) {
				deck.setIsStarted(rs.getInt("czyRozpoczeta"));
				deck.setName(rs.getString("nazwa"));
				deck.setHowManySmallCards(rs.getInt("ileMalych"));
				deck.setHowManyMediumCards(rs.getInt("ileSrednich"));
				deck.setHowManyCards(rs.getInt("ileKart"));
			}
			System.out.println("nazwa talii " + deck.getName());
			System.out.println("ile malych" + deck.getHowManySmallCards());
			System.out.println("ile malych" + deck.getIsStarted());
			conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * czyta dane z BD z tabeli karta
	 */
	public static void readCards() {
		conection = (Connection) SqliteConnection.Connector();
		if (conection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = conection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from karta");
			while (rs.next()) {
				deck.cardsList.add(new Card(rs.getInt("typ"), rs.getString("tytul"), rs.getString("opis"),
						rs.getString("obrazek")));
			}
			conection.close();
		} catch (SQLException e) {
			//
			e.printStackTrace();
		}
	}

	/**
	 * usuwa dane z BD i zapisuje zmienne talii i karty do niej
	 */

	public static void saveDB() {
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
			preStmt.setInt(4, deck.getIsStarted());

			preStmt.executeUpdate();
			System.out.println("zapisane dane, czyRozpoczeta" + deck.getIsStarted());
			String sql = "";
			for (int i = 0; i < deck.cardsList.size(); i++) {

				query = "INSERT INTO karta VALUES (" + i + "," + deck.cardsList.get(i).getType() + ",'"
						+ deck.cardsList.get(i).getTitle() + "','" + deck.cardsList.get(i).getDescription() + "','"
						+ deck.cardsList.get(i).getImage() + "');";
				sql += query;
			}
			System.out.println(sql);
			System.out.println("ile kart w talii zapisz()" + deck.cardsList.size());
			mySta.executeUpdate(sql);

			conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
