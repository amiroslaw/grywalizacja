package dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;

import database.Card;

public class CardDao extends CommonDao {

        public CardDao(ConnectionSource connectionSource) {
            super(connectionSource);
        }
//        public List<String[]> queryRaw(String value) throws SQLException {
//            GenericRawResults<String[]> where = getDao(Card.class).queryRaw("SELECT * FROM books WHERE title = 'Hobbit'");
//            return where.getResults();
//    } 
        public int countMediumRewards(int id) throws SQLException {
            GenericRawResults<String[]> where = getDao(Card.class).queryRaw("SELECT COUNT(*) FROM card WHERE type = 2 AND deck_id =" + id);
            return Integer.parseInt(where.getFirstResult()[0]);
    } 
        public int countSmallRewards(int id) throws SQLException {
            GenericRawResults<String[]> where = getDao(Card.class).queryRaw("SELECT COUNT(*) FROM card WHERE type = 3 AND deck_id =" + id);
            return Integer.parseInt(where.getFirstResult()[0]);
    } 

}
