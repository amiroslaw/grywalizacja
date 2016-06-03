package application;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Połączenie z bazą danych
 * @author miro
 *
 */
public class SqliteConnection {
	public static Connection Connector() {
		try {
			String path= System.getProperty("user.home"); 
			path +="/Dokumenty/Ustawienia/sync/grywalizacja.sqlite";
			System.out.println(path);
			Class.forName("org.sqlite.JDBC");
			//mozna dodac sciezke do w ktorej chce sie trzymac plik sqlite
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:/user.home/taliadb.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
}

