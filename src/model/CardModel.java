package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CardDao;
import database.Card;
import database.DbManager;




public class CardModel {
    private List <Card> cardList = new ArrayList<>();
    public void getAllCards() {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardList = cardDao.queryForAll(Card.class);
        DbManager.closeConnectionSource();
    }
    
    public void deleteCardById(Card card)   {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardDao.deleteById(Card.class, card.getId());
        DbManager.closeConnectionSource();
        getAllCards();
    }

    public void saveAllCardsInDataBase(List <Card> cards)   {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cards.forEach(card -> cardDao.creatOrUpdate(card));
        DbManager.closeConnectionSource();
        getAllCards();
    }
    public void saveCardInDataBase(Card card)   {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardDao.creatOrUpdate(card);
        DbManager.closeConnectionSource();
        getAllCards();
    }
    public int getAmountOfMediumRewards(int id) throws SQLException{
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        int amount = cardDao.countMediumRewards(id);
        DbManager.closeConnectionSource();
        return amount;
    }
    public int getAmountOfSmallRewards(int id) throws SQLException{
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        int amount = cardDao.countSmallRewards(id);
        DbManager.closeConnectionSource();
        return amount;
    }
//    public void updateCardInDataBase()   {
//        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
//        Category tempCategory = cardDao.findById(Card.class, getCategory().getId());
//        tempCategory.setName(getCategory().getName());
//        cardDao.creatOrUpdate(tempCategory);
//        DbManager.closeConnectionSource();
//        getAllCards();
//    }
}
//public void saveAuthorInDataBase() throws ApplicationException {
//    AuthorDao authorDao = new AuthorDao(DbManager.getConnectionSource());
//    Author author = ConverterAuthor.convertAuthorFxToAuthor(this.getAuthorFxObjectProperty());
//    authorDao.creatOrUpdate(author);
//    DbManager.closeConnectionSource();
//}
