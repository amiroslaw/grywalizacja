package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBmanager {
	public static Deck deck = new Deck();
	public static int amountOfDecks = 0;
	public static String[] listOfDecks;
	public static HashMap<String, Integer> mapOfDecks = new HashMap<>();
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
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS karta (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"
					+ " typ  INTEGER, tytul TEXT,  opis  TEXT,  obrazek  TEXT,"
					+ "idTalia INTEGER NOT NULL, FOREIGN KEY(idTalia) REFERENCES talia(id))");
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS talia (id INTEGER PRIMARY KEY  NOT NULL ,nazwa TEXT,"
					+ "ileKart INTEGER, ileMalych INTEGER,ileSrednich INTEGER, czyRozpoczeta INTEGER DEFAULT (0))");
			mySta.executeUpdate("CREATE TABLE IF NOT EXISTS conf (currentDeck INTEGER NOT NULL)");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readConf() {

	}

	public static void readListOfDecks() {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {

			Statement mySta = connection.createStatement();
			// czytanie tylko ilosci wirszy z talii
			ResultSet rs = mySta.executeQuery("SELECT COUNT(*) FROM talia;");
			amountOfDecks = rs.getInt(1);

			// listOfDecks = new String [amountOfDecks];
			rs = mySta.executeQuery("SELECT nazwa, id  FROM talia");
			while (rs.next()) {
				// listOfDecks[i]=rs.getString("nazwa");
				mapOfDecks.put(rs.getString("nazwa"), rs.getInt("id"));
			}
			System.out.println("lista deków " + amountOfDecks);
			// na tablicy
			// listOfDecks = new String [amountOfDecks];
			// int i=0;
			// rs = mySta.executeQuery("select nazwa from talia");
			// while (rs.next()) {
			// listOfDecks[i]=rs.getString("nazwa");
			// i++;
			// }
			// System.out.println("lista deków "+amountOfDecks+" pierwszy el. "+
			// listOfDecks[0]);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void readDeckInfo(String deckName) {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			// TODO dodac where
			ResultSet rs = mySta.executeQuery("select * from talia where nazwa ='" + deckName + "'");
			while (rs.next()) {
				deck.setIsStarted(rs.getInt("czyRozpoczeta"));
				deck.setName(rs.getString("nazwa"));
				deck.setHowManySmallCards(rs.getInt("ileMalych"));
				deck.setHowManyMediumCards(rs.getInt("ileSrednich"));
				deck.setHowManyCards(rs.getInt("ileKart"));
				deck.setID(rs.getInt("id"));
			}
			System.out.println("redDeckInfo");
			System.out.println("nazwa talii " + deck.getName());
			System.out.println("ile malych" + deck.getHowManySmallCards());
			System.out.println("is started dbmanager " + deck.getIsStarted());
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * czyta dane z BD z tabeli karta
	 * 
	 * TODO: dodac where idTalia == currentDeck
	 * 
	 * @param integer
	 */
	public static void readCards(int deckID) {
		connection = (Connection) SqliteConnection.Connector();
		if (connection == null) {

			System.out.println("connection not successful");
			System.exit(1);
		}
		try {
			Statement mySta = connection.createStatement();
			ResultSet rs = mySta.executeQuery("SELECT * FROM karta WHERE idTalia=" + deckID);
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
			mySta.executeUpdate("DELETE FROM talia WHERE id=" + deck.getID());
				mySta.executeUpdate("DELETE  FROM karta WHERE idTalia=" + deck.getID());
				// zapisywanie zmiennych z talii
			if (deck.getIsStarted() != 3) {
				String query = "INSERT INTO talia VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement preStmt = connection.prepareStatement(query);
				preStmt.setInt(1, deck.getID());
				preStmt.setString(2, deck.getName());
				preStmt.setInt(3, deck.getHowManyCards());
				preStmt.setInt(4, deck.getHowManySmallCards());
				preStmt.setInt(5, deck.getHowManyMediumCards());
				preStmt.setInt(6, deck.getIsStarted());

				preStmt.executeUpdate();

				// zapisywanie talii kart
				System.out.println("zapisane dane, czyRozpoczeta" + deck.getIsStarted());
				String sql = "";
				for (int i = 0; i < deck.cardsList.size(); i++) {

					query = "INSERT INTO karta VALUES (NULL," + deck.cardsList.get(i).getType() + ",'"
							+ deck.cardsList.get(i).getTitle() + "','" + deck.cardsList.get(i).getDescription() + "','"
							+ deck.cardsList.get(i).getImage() + "','" + deck.getID() + "');";
					sql += query;
				}
			
			System.out.println(sql);
			System.out.println("ile kart w talii zapisz()" + deck.cardsList.size());
			mySta.executeUpdate(sql);
			}
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
