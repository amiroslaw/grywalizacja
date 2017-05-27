package controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
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
    private final Image award1 = new Image("img/award1.png");
    private final Image award2 = new Image("img/award2.png");
    private final Image award3 = new Image("img/award3.png");
    private final Image award4 = new Image("img/cow.png");

    private File imageFile;
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
        obrazek.setImage(award4);
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
        if (cardsList.size() == 1 && howManyCards == 0) {
            deck.setIsStarted(3);
            los.setDisable(true);
            String teskt = cardsList.get(0).toString();
            cardDescription.setText(teskt);
            pozostalo.setText("0");
            showImage(award1, 0);
            cardModel.deleteCardById(cardsList.get(0));
        } else {
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

    private void checkType(int randomIndex) {
        int typeOfCard = cardsList.get(randomIndex).getType();
        switch (typeOfCard) {
        case 1:
            showImage(award1, randomIndex);
            break;
        case 2:
            howManyMediumCards--;
            showImage(award2, randomIndex);
            break;
        case 3:
            howManySmallCards--;
            showImage(award3, randomIndex);
            break;
        default:
            break;
        }
    }

    private void showImage(Image award, int index) {
         final   String imageSource = cardsList.get(index).getImage();
         Image image; 
        if (isDefault(index)) {
            image = award;
        } else if(isURL(imageSource)) {
            image = new Image(imageSource);
        }else {
            imageFile = new File(imageSource);
            image = new Image(imageFile.toURI().toString());
        }
            obrazek.setImage(image);
    }
    private boolean isURL(String source) {
        return source.matches("http.*");
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
