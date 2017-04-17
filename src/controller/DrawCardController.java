package controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import database.Card;
import database.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.CardModel;
import model.DeckModel;

/**
 * Kontroler do losowania z metodą {@link #readDeckInfo()}
 * 
 * @author miro
 *
 */
public class DrawCardController {

    @FXML
    private ImageView obrazek;
    @FXML
    private Button goToStart;
    @FXML
    private Button los;
    @FXML
    private Label cardDescription;
    @FXML
    private Label pozostalo;
    @FXML
    private Label pozostaloSrednich;
    @FXML
    private Label pozostaloMalych;

    private ViewManager manager;
    private Stage primaryStage;
    private Image award1 = new Image("img/award1.png");
    private Image award2 = new Image("img/award2.png");
    private Image award3 = new Image("img/award3.png");
    private Image award4 = new Image("img/cow.png");

    private File imageFile;
    private String deckName;
    // private HashMap<String, Integer> mapOfDecks = new HashMap<>();
    // private DBmanager dBmanager = new DBmanager();
    private DeckModel deckModel;
    private CardModel cardModel;
    private Deck deck;
    private int howManyCards;
    private int howManySmallCards;
    private int howManyMediumCards;
    private ArrayList<Card> cardsList;

    public void init(int id) {

        primaryStage.setOnCloseRequest(e -> updateDatabase());
        cardModel = new CardModel();
        cardModel.getAllCards();
        deckModel = new DeckModel();
        deckModel.getDeckById(id);
        deck = deckModel.getCurrentDeck();
        // System.out.println("deck " + deckModel.getCurrentDeck());
        // deckModel.getAllDecks();
        cardsList = new ArrayList<>(deckModel.getCurrentDeck().getCards());
        // ForeignCollection<Card> list =
        // deckModel.getDeckList().get(0).getCards();
        // list.forEach(System.out::println);
        // cardsList.forEach(System.out::println);
        // System.out.println("karta 0 "+ cardList.get(0).getTitle());

        fillData();
    }

    private void fillData() {

        obrazek.setImage(award4);
        howManyCards = deck.getHowManyCards();
        // howManyCards = deck.getHowManyCards();
        // howManySmallCards = deck.getHowManySmallCards();
        // howManyMediumCards = deck.getHowManyMediumCards();
        try {
            howManyMediumCards = cardModel.getAmountOfMediumRewards(deck.getId());
            howManySmallCards = cardModel.getAmountOfSmallRewards(deck.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pozostalo.setText(Integer.toString(howManyCards));
        pozostaloSrednich.setText(Integer.toString(howManyMediumCards));
        pozostaloMalych.setText(Integer.toString(howManySmallCards));
    }

    /**
     * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
     * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
     */
    @FXML
    void drawCard(ActionEvent event) {
        if (cardsList.size() == 1 && howManyCards == 0) {
            deck.setIsStarted(3);
            los.setDisable(true);
            String teskt = cardsList.get(0).toString();
            cardDescription.setText(teskt);
            showImage(award1, 0);
            cardModel.deleteCardById(cardsList.get(0));
            // cardDescription.setText("gratuluję zakończyłeś talię");
        } else {
            // deck.setHowManyCards(--howManyCards);
            deck.setIsStarted(1);
            int randomIndex = getRandomIndex();
            if (randomIndex >= 0) {
                checkType(randomIndex);
                String teskt = cardsList.get(randomIndex).toString();
                cardDescription.setText(teskt);
                cardModel.deleteCardById(cardsList.get(randomIndex));
                cardsList.remove(randomIndex);
            } else {
                howManyCards--;
                obrazek.setImage(award4);
                cardDescription.setText("Blank card");
            }
            setText();
        }

    }

    private int getRandomIndex() {
        int maxIndex = howManyMediumCards + howManySmallCards;
        if (maxIndex == 0 && howManyCards == 0)
            return 0;
        Random random = new Random();
        int randomIndex = random.nextInt(maxIndex + howManyCards) + 1;
        if (randomIndex > maxIndex) {
            return -1;
        } else {
            return randomIndex;
        }
    }

    private void setText() {

        pozostalo.setText(Integer.toString(howManyCards));
        pozostaloSrednich.setText(Integer.toString(howManyMediumCards));
        pozostaloMalych.setText(Integer.toString(howManySmallCards));
    }

    private void checkType(int randomIndex) {
        int typeOfCard = cardsList.get(randomIndex).getType();
        switch (typeOfCard) {
        case 1:
            showImage(award1, randomIndex);
            break;
        case 2:
            howManyMediumCards--;
            // deck.setHowManyMediumCards(--howManyMediumCards);
            showImage(award2, randomIndex);
            break;
        case 3:
            howManySmallCards--;
            // deck.setHowManySmallCards(--howManySmallCards);
            showImage(award3, randomIndex);
            break;
        default:

            break;
        }
    }

    private void showImage(Image award, int index) {
        if (cardsList.get(index).getImage().equals("default")) {
            obrazek.setImage(award);
        } else {
            imageFile = new File(cardsList.get(index).getImage());
            obrazek.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    @FXML
    private void goToStart(ActionEvent event) {
        // cardsList.forEach(System.out::println);
        // deckModel.getCurrentDeck().getCards().forEach(e -> {
        // e.setDescription("zmienione");
        // cardModel.saveCardInDataBase(e);
        // });
        // cardsList.forEach(card -> cardModel.saveCardInDataBase(card));
        // cardModel.saveAllCardsInDataBase(cardsList);
        // cardModel.saveCardInDataBase(new Card(1,"nowa"));

        // dBmanager.saveDB(deck);
        updateDatabase();
        primaryStage.close();
        manager.showStart();
    }

    private void updateDatabase() {
        if (deck.getIsStarted() == 3) {
            deckModel.deleteDeckById(deck);
        } else {
            deck.setHowManyCards(howManyCards);
            deckModel.saveDeckInDataBase(deck);
        }
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * sprawdza czy takia została zaczęta czy nie, wywołuje odpowiednie metody i
     * wyświtla ilość kart
     */

}
