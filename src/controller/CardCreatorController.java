package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.Card;
import database.Deck;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CardModel;
import model.DeckModel;
import view.DialogsUtils;

public class CardCreatorController {

    DrawCardController drawCardController = new DrawCardController();
    private ViewManager manager;
    private Stage primaryStage;
    private Card card = new Card();
    private List<Card> cardsList = new ArrayList<>();
    private Deck deck = new Deck();
    private String linkToImage;
    private int cardCounter = 1;
    private SimpleBooleanProperty isImage = new SimpleBooleanProperty(false);
    @FXML private Label lblnrCard;
    @FXML private Label lblType;
    @FXML private TextField txtName;
    @FXML private TextArea txtDescription;
    @FXML private TextField urlTF;
    @FXML private Button btnImage;
    @FXML private Button btnNext;
    @FXML private Button btnPreviousCard;
    @FXML private Button btnCancel;

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        btnPreviousCard.setDisable(true);
        deck.setIsStarted(0);
        bind();
    }

    private void bind() {
        btnNext.disableProperty().bind(txtName.textProperty().isEmpty());
        btnImage.disableProperty().bind(urlTF.textProperty().isNotEmpty());
        urlTF.disableProperty().bind(isImage);
    }

    @FXML
    private void showStartWindow() {
        Optional<ButtonType> result = DialogsUtils.cancelCreateDeckDialog();
        if (result.get() == ButtonType.OK) {
            primaryStage.close();
            manager.showStart();
        }
    }

    @FXML
    private void chooseImage() {
       String path = DialogsUtils.getImagePathDialog();
        if (!path.equals("")) {
            linkToImage = path;
            isImage.set(true);
        }
    }

    @FXML
    private void nextCard() {
        btnPreviousCard.setDisable(false);
        viewTypeOfAward();
        setCardProperties();
        cardsList.add(card);
        cardCounter++;
        lblnrCard.setText(Integer.toString(cardCounter));
        resetValues();
        if (cardCounter == 11) {
            endCreate();
        }
        card = new Card();
    }

    private void resetValues() {
        linkToImage = null;
        isImage.set(false);
        // TODO: uncomment when will you finish
         txtName.setText("");
         txtDescription.setText("");
         urlTF.setText("");
    }

    @FXML
    private void previousCard() {
        if (cardCounter == 2) {
            btnPreviousCard.setDisable(true);
        }
        cardCounter--;
        cardsList.remove(cardCounter - 1);
        Card tempCard = cardsList.get(cardsList.size() - 1);
        txtName.setText(tempCard.getTitle());
        txtDescription.setText(tempCard.getDescription());
        lblnrCard.setText(Integer.toString(cardCounter));
        viewTypeOfAward();
    }

    private void viewTypeOfAward() {
        if (cardCounter > 0 && cardCounter < 4)
            lblType.setText(Integer.toString(2));
        else
            lblType.setText(Integer.toString(3));
    }

    private void setCardProperties() {
        card.setTitle(txtName.getText());
        card.setDescription(txtDescription.getText());
        card.setType(getType());
        card.setImage(getLink());
    }

    private int getType() {
        if(cardCounter == 1) return 1;
        if(cardCounter > 1 && cardCounter < 5) return 2;
        return 3;
    }

    private String getLink() {
        if (!urlTF.getText().equals("")) {
            return urlTF.getText();
        }
        if (linkToImage != null) {
            return linkToImage;
        }
        return "default";
    }

    private void endCreate() {
        addPropertiesToDeck();
        saveDataBase();
        primaryStage.close();
        manager.showStart();
    }

    private void addPropertiesToDeck() {
        Optional<String> deckName = DialogsUtils.inputText();
        if (deckName.isPresent()) {
            deck.setDeckName(deckName.get());
            deck.setHowManyBlankCards(30);
            deck.setIsStarted(1);
            cardsList.forEach(card -> card.setDeck(deck));
        }
    }
    
    private void saveDataBase() {
        DeckModel deckModel = new DeckModel();
        deckModel.saveDeckInDataBase(deck);
        CardModel cardModel = new CardModel();
        cardsList.forEach(card -> cardModel.saveCardInDataBase(card));
    }

}