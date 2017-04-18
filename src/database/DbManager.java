package database;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import view.DialogsUtils;

public class DbManager {
    private static String JDBC_DRIVER = "jdbc:sqlite:gamificationDB.sqlite";

    // private static ConnectionSource connectionSource;
    private static JdbcConnectionSource connectionSource;

    public static void initDatabase() {
        // TODO: change DB path
        // String path = System.getProperty("user.home");
        // path += "/Dokumenty/Ustawienia/sync";
        // JDBC_DRIVER = "jdbc:sqlite:"+path+"/gamificationDB.sqlite";
        createConnectionSource();
        // dropTable();
        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER);
        } catch (SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public static ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnectionSource() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }
    }

    private static void createTable() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Deck.class);
            TableUtils.createTableIfNotExists(connectionSource, Card.class);
        } catch (SQLException e) {
             DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private static void dropTable() {
        try {
            TableUtils.dropTable(connectionSource, Deck.class, true);
            TableUtils.dropTable(connectionSource, Card.class, true);
        } catch (SQLException e) {
             DialogsUtils.errorDialog(e.getMessage());
        }
    }
}
