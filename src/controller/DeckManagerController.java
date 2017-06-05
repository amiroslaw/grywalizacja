package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.Card;
import database.Deck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.stage.Stage;
import model.CardModel;
import model.DeckModel;
import view.DialogsUtils;

public class DeckManagerController {

    @FXML private Button btnAddDeck;
    @FXML private Button btnEditDeck;
    @FXML private Button btnDeleteDeck;
    @FXML private MenuItem imCopyDeck;
    @FXML private MenuItem imEditDeck;
    @FXML private MenuItem imDeleteDeck;
    @FXML private ListView<String> deckListView;
    private Stage primaryStage;
    private ViewManager manager;
    private DeckModel deckModel;
    private CardModel cardModel;
    private List<Deck> deckList;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void initialize() {
        deckModel = new DeckModel();
        refreshObjects();
        createListView(deckList);
    }

    private void refreshObjects() {
        deckModel.getAllDecks();
        deckList = deckModel.getDeckList();
    }

    private void createListView(List<Deck> decks) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        decks.forEach(e -> observableList.add(e.getDeckName()));
        deckListView.setItems(observableList);
    }

    @FXML
    void addDeck() {
        primaryStage.close();
        manager.showCardCreator();
    }

    @FXML
    void deleteDeck() {
        SelectionModel<String> selected = deckListView.getSelectionModel();
        if (!selected.isEmpty()) {
            deleteFromDB(selected);
            deckListView.getItems().remove(selected.getSelectedIndex());
        }
    }

    private void deleteFromDB(SelectionModel<String> selected) {
        Deck selectedDeck = deckList.get(selected.getSelectedIndex());
        cardModel = new CardModel();
        cardModel.deleteAllCardsInDataBase(new ArrayList<Card>(selectedDeck.getCards()));
        deckModel.deleteDeckById(selectedDeck);
    }

    @FXML
    void copyDeck() {
        cardModel = new CardModel();
        SelectionModel<String> selected = deckListView.getSelectionModel();
        Deck selectedDeck = deckList.get(selected.getSelectedIndex());
        if (!selected.isEmpty()) {
            Optional<String> deckName = DialogsUtils.inputText();
            if (deckName.isPresent()) {
                deckListView.getItems().add(deckName.get());
                Deck newDeck = new Deck(selectedDeck);
                newDeck.setDeckName(deckName.get());

                ArrayList<Card> cards = copyCards(selectedDeck, newDeck);
                saveToDB(newDeck, cards);
            }
            refreshObjects();
        }

    }

    private ArrayList<Card> copyCards(Deck selectedDeck, Deck newDeck) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (Card card : selectedDeck.getCards()) {
            Card c = new Card(card);
            c.setDeck(newDeck);
            cards.add(c);
        }
        return cards;
    }

    private void saveToDB(Deck newDeck, ArrayList<Card> cards) {
        deckModel.saveDeckInDataBase(newDeck);
        cardModel.saveAllCardsInDataBase(cards);
        cards = new ArrayList<>();
    }

    @FXML
    private void showEditCards() {
        SelectionModel<String> selected = deckListView.getSelectionModel();
        if (!selected.isEmpty()) {
            Deck deck = deckList.get(selected.getSelectedIndex());
            manager.showEditCards(deck);
        }
    }

    @FXML
    private void onEditName() {
    }

}
