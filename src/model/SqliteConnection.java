package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Połączenie z bazą danych
 * 
 * @author miro
 *
 */
public class SqliteConnection {
    public static Connection Connector() {
        try {
            String path = System.getProperty("user.home");
            path += "/Dokumenty/Ustawienia/sync/grywalizacja.sqlite";
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:taliadb.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}