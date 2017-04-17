package model;

import java.util.ArrayList;
import java.util.List;

import dao.DeckDao;
import database.DbManager;
import database.Deck;

public class DeckModel {
    private List<Deck> deckList = new ArrayList<>();
    private Deck currentDeck = new Deck();

    public void getAllDecks() {
        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        deckList = deckDao.queryForAll(Deck.class);
        DbManager.closeConnectionSource();
    }
    public void getDeckById(int id)   {

        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        currentDeck = deckDao.findById(Deck.class, id);
        DbManager.closeConnectionSource();
//        getAllDecks(); 
    } 
    public void getDeckById(Deck deck)   {

        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        currentDeck = deckDao.findById(Deck.class, deck.getId());
        DbManager.closeConnectionSource();
//        getAllDecks(); 
    } 
    public void deleteDeckById(Deck deck)   {

        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        deckDao.deleteById(Deck.class, deck.getId());
        DbManager.closeConnectionSource();
        getAllDecks(); 
    }

    public void saveAllDecksInDataBase(List <Deck> decks)   {
        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        decks.forEach(card -> deckDao.creatOrUpdate(card));
        DbManager.closeConnectionSource();
        getAllDecks();
    }
  
    public void saveDeckInDataBase(Deck deck) {
        DeckDao deckDao = new DeckDao(DbManager.getConnectionSource());
        deckDao.creatOrUpdate(deck);
        DbManager.closeConnectionSource();
        getAllDecks();
    }
    
    public List<Deck> getDeckList(){
        return deckList;
    }
    public Deck getCurrentDeck() {
        return currentDeck;
    }

}
