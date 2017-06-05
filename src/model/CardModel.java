package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CardDao;
import database.Card;
import database.DbManager;
import database.Deck;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CardModel {
    private List<Card> cardList = new ArrayList<>();

    private ObjectProperty<CardFx> cardFxObjectProperty = new SimpleObjectProperty<>(new CardFx());
    private ObjectProperty<CardFx> cardFxObjectPropertyEdit = new SimpleObjectProperty<>(new CardFx());

    private ObservableList<CardFx> cardFxObservableList = FXCollections.observableArrayList();

    private Deck deck;

    public void init(Deck deck) {
        this.deck = deck;
        this.cardFxObservableList.clear();
        List<Card> cards;
        try {
            cards = getCardFromDeck(deck.getId());
            cards.forEach(card -> this.cardFxObservableList.add(ConverterUtils.convertCard(card)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveCardEdit() {
        saveOrUpdate(this.getCardFxObjectPropertyEdit());
    }

    public void saveCard() {
        saveOrUpdate(this.getCardFxObjectProperty());
    }

    public void deleteCard() {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardDao.deleteById(Card.class, this.getCardFxObjectPropertyEdit().getValue().getIdInt());
        this.init(deck);
    }

    private void saveOrUpdate(ObjectProperty<CardFx> objectProperty) {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        Card card = new Card();
        card.setId(objectProperty.getValue().getIdInt());
        card.setTitle(objectProperty.getValue().getTitleString());
        card.setDescription(objectProperty.getValue().getDescriptionString());
        card.setImage(objectProperty.getValue().getImageString());
        card.setType(objectProperty.getValue().getTypeInt());
        card.setDeck(deck);
        cardDao.creatOrUpdate(card);
        this.init(deck);
    }

    public void getAllCards() {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardList = cardDao.queryForAll(Card.class);
        DbManager.closeConnectionSource();
    }

    public List<Card> getCardFromDeck(int deckId) throws SQLException {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        List<Card> cards = cardDao.getQueryBuilder(Card.class).where().eq("deck_id", deckId).query();
        return cards;
    }

    public void deleteCardById(Card card) {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardDao.deleteById(Card.class, card.getId());
        DbManager.closeConnectionSource();
        getAllCards();
    }

    public void deleteAllCardsInDataBase(List<Card> cards) {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cards.forEach(card -> cardDao.deleteById(Card.class, card.getId()));
        DbManager.closeConnectionSource();
    }

    public void saveAllCardsInDataBase(List<Card> cards) {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cards.forEach(card -> cardDao.creatOrUpdate(card));
        DbManager.closeConnectionSource();
        getAllCards();
    }

    public void saveCardInDataBase(Card card) {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        cardDao.creatOrUpdate(card);
        DbManager.closeConnectionSource();
        getAllCards();
    }

    public int getAmountOfMediumRewards(int id) throws SQLException {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        int amount = cardDao.countMediumRewards(id);
        DbManager.closeConnectionSource();
        return amount;
    }

    public int getAmountOfSmallRewards(int id) throws SQLException {
        CardDao cardDao = new CardDao(DbManager.getConnectionSource());
        int amount = cardDao.countSmallRewards(id);
        DbManager.closeConnectionSource();
        return amount;
    }

    // public CardFx getCardFxObjectProperty(){
    // return cardFxObjectProperty.get();
    // }
    public CardFx getCardFxPropertyEdit() {
        return cardFxObjectPropertyEdit.get();
    }

    public ObjectProperty<CardFx> getCardFxObjectProperty() {
        return cardFxObjectProperty;
    }

    public void setCardFxObjectProperty(CardFx cardFxObjectProperty) {
        this.cardFxObjectProperty.set(cardFxObjectProperty);
    }

    public ObservableList<CardFx> getCardFxObservableList() {
        return cardFxObservableList;
    }

    public void setCardFxObservableList(ObservableList<CardFx> cardFxObservableList) {
        this.cardFxObservableList = cardFxObservableList;
    }

    public ObjectProperty<CardFx> getCardFxObjectPropertyEdit() {
        return cardFxObjectPropertyEdit;
    }

    public void setCardFxObjectPropertyEdit(CardFx cardFxObjectPropertyEdit) {
        this.cardFxObjectPropertyEdit.set(cardFxObjectPropertyEdit);
    }
}
