package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import application.AwardImage;
import database.Card;
import database.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.CardModel;
import model.DeckModel;

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


    private DeckModel deckModel;
    private CardModel cardModel;
    private Deck deck;
    private int howManyCards;
    private int howManySmallCards;
    private int howManyMediumCards;
    private ArrayList<Card> cardsList;

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init(int id) {
        primaryStage.setOnCloseRequest(e -> updateDatabase());
        cardModel = new CardModel();
        cardModel.getAllCards();
        deckModel = new DeckModel();
        deckModel.getDeckById(id);
        deck = deckModel.getCurrentDeck();
        cardsList = new ArrayList<>(deckModel.getCurrentDeck().getCards());

        fillData();
    }

    private void fillData() {
        obrazek.setImage(AwardImage.AWARD4);
        try {
            howManyCards = deck.getHowManyBlankCards();
            howManyMediumCards = cardModel.getAmountOfMediumRewards(deck.getId());
            howManySmallCards = cardModel.getAmountOfSmallRewards(deck.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setText();
    }

    private void setText() {
        pozostalo.setText(Integer.toString(howManyCards + howManyMediumCards + howManySmallCards + 1));
        pozostaloSrednich.setText(Integer.toString(howManyMediumCards));
        pozostaloMalych.setText(Integer.toString(howManySmallCards));
    }

    @FXML
    private void drawCard() {
        final boolean isMainAward = cardsList.size() == 1 && howManyCards == 0;
        if (isMainAward) {
            deck.setIsStarted(3);
            los.setDisable(true);
            cardDescription.setText(cardsList.get(0).toString());
            pozostalo.setText("0");
            showImage(AwardImage.AWARD1, 0);
            cardModel.deleteCardById(cardsList.get(0));
        } else {
            deck.setIsStarted(1);
            int randomIndex = getRandomIndex();
            if (randomIndex >= 0) {
                checkType(randomIndex);
                cardDescription.setText(cardsList.get(randomIndex).toString());
                cardModel.deleteCardById(cardsList.get(randomIndex));
                cardsList.remove(randomIndex);
            } else {
                howManyCards--;
                obrazek.setImage(AwardImage.AWARD4);
                cardDescription.setText("Blank card");
            }
            setText();
        }
    }

    private int getRandomIndex() {
        final int maxIndex = howManyMediumCards + howManySmallCards;
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

    private void checkType(int randomIndex) {
        int typeOfCard = cardsList.get(randomIndex).getType();
        switch (typeOfCard) {
        case 1:
            showImage(AwardImage.AWARD1, randomIndex);
            break;
        case 2:
            howManyMediumCards--;
            showImage(AwardImage.AWARD2, randomIndex);
            break;
        case 3:
            howManySmallCards--;
            showImage(AwardImage.AWARD3, randomIndex);
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    private void showImage(Image award, int index) {
        final String imageSource = cardsList.get(index).getImage();
        Image image = new AwardImage(award, index, imageSource).getSource(isDefault(index));
        obrazek.setImage(image);
    }

    private boolean isDefault(int index) {
        return cardsList.get(index).getImage().equals("default");
    }

    @FXML
    private void goToStart() {
        updateDatabase();
        primaryStage.close();
        manager.showStart();
    }

    private void updateDatabase() {
        if (deck.getIsStarted() == 3) {
            deckModel.deleteDeckById(deck);
        } else {
            deck.setHowManyBlankCards(howManyCards);
            deckModel.saveDeckInDataBase(deck);
        }
    }
}
