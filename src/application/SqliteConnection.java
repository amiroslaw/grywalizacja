package application;

import java.sql.*;

/**
 * Połączenie z bazą danych
 * @author miro
 *
 */
public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			//mozna dodac sciezke do w ktorej chce sie trzymac plik sqlite
			Connection conn = DriverManager.getConnection("jdbc:sqlite:taliadb.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
			// TODO: handle exception
		}
	}
	
}

