package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBmanager {
	public static Deck deck = new Deck();
	public static int currentDeck; 
	static Connection connection;
	public static void createDB() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// usuwanie kart i tali
			mySta.executeUpdate(
					"CREATE TABLE IF NOT EXISTS karta (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
					+ " typ  INTEGER, tytul TEXT,  opis  TEXT,  obrazek  TEXT,"
					+ "idTalia INTEGER NOT NULL, FOREIGN KEY(idTalia) REFERENCES talia(id))");
			mySta.executeUpdate(
					"CREATE TABLE IF NOT EXISTS talia (id INTEGER PRIMARY KEY  NOT NULL ,nazwa TEXT,"
					+ "ileKart INTEGER, ileMalych INTEGER,ileSrednich INTEGER, czyRozpoczeta INTEGER DEFAULT (0))");
			mySta.executeUpdate(
					"CREATE TABLE IF NOT EXISTS conf (currentDeck INTEGER NOT NULL)");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void readConf() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from conf");
			while (rs.next()) {
				currentDeck = rs.getInt("currentDeck");
				
			}
			// TODO: zmienic jak dodam wybor talii
			currentDeck = 1; 
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void readDeckInfo() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
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
			System.out.println("is started dbmanager" + deck.getIsStarted());
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * czyta dane z BD z tabeli karta
	 * 
	 * TODO: dodac where idTalia == currentDeck
	 */
	public static void readCards() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			ResultSet rs = mySta.executeQuery("select * from karta");
			while (rs.next()) {
				deck.cardsList.add(new Card(rs.getInt("typ"), rs.getString("tytul"), rs.getString("opis"),
						rs.getString("obrazek")));
			}
			connection.close();
		} catch (SQLException e) {
			//
			e.printStackTrace();
		}
	}

	/**
	 * usuwa dane z BD i zapisuje zmienne talii i karty do niej
	 */

	public static void saveDB() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// usuwanie kart i tali
//			mySta.executeUpdate("DELETE FROM talia WHERE id=1");
//			mySta.executeUpdate("DELETE  FROM karta");
			// zapisywanie zmiennych z talii
			String query = "INSERT INTO talia VALUES (NULL, ?, ?, ?, ?, ?)";
			PreparedStatement preStmt = connection.prepareStatement(query);
			preStmt.setString(1, deck.getName());
			preStmt.setInt(2, deck.getHowManyCards());
			preStmt.setInt(3, deck.getHowManySmallCards());
			preStmt.setInt(4, deck.getHowManyMediumCards());
			preStmt.setInt(5, deck.getIsStarted());

			preStmt.executeUpdate();
			System.out.println("zapisane dane, czyRozpoczeta" + deck.getIsStarted());
			String sql = "";
			for (int i = 0; i < deck.cardsList.size(); i++) {

				query = "INSERT INTO karta VALUES (" + i + "," + deck.cardsList.get(i).getType() + ",'"
						+ deck.cardsList.get(i).getTitle() + "','" + deck.cardsList.get(i).getDescription() + "','"
						+ deck.cardsList.get(i).getImage() + "','" + currentDeck+"');";
				sql += query;
			}
			System.out.println(sql);
			System.out.println("ile kart w talii zapisz()" + deck.cardsList.size());
			mySta.executeUpdate(sql);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// testy
	public static void showDeck() {
		System.out.println("size cardsList: " + deck.cardsList.size());
		for (int i = 0; i < deck.cardsList.size(); i++) {
			System.out.println(deck.cardsList.get(i));
		}
	}
}
