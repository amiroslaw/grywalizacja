package database;

import dao.CardDao;
import dao.DeckDao;
import model.Card;
import model.Deck;
import view.DialogsUtils;

public class FillDBUtils {
    static Card card = new Card();
    static Deck deck = new Deck();
    public static void createDeck() {
    deck.setDeckName("pierwsza");
    deck.setIsStarted(0);
        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        try {
            deckDao.creatOrUpdate(deck);
        } catch (RuntimeException e) {
            e.printStackTrace();
            DialogsUtils.errorDialog("Save deck error");
        }
        DbManager.closeConnectionSource();
    }
    public static void createCard() {
        card.setTitle("karta1");
        card.setImage("image");
        card.setType(1);
        card.setDescription("opis");
        card.setDeck(deck);
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        try {
            cardDao.creatOrUpdate(card);
        } catch (RuntimeException e) {
            e.printStackTrace();
            DialogsUtils.errorDialog("Save card error");
        }
        DbManager.closeConnectionSource();
    }
   
}
