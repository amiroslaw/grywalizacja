package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.Card;
import database.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
    private File fileChooserImage;
    private String linkToImage;
    private int cardCounter = 1;

    @FXML
    private Label lblnrKarty;
    @FXML
    private Label lblTyp;
    @FXML
    private TextField txtNazwa;
    @FXML
    private TextArea txtOpis;
    @FXML
    private Button btnObrazek;
    @FXML
    private Button btnDalej;
    @FXML
    private Button btnPreviousCard;
    @FXML
    private Button btnCancel;

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        btnPreviousCard.setDisable(true);
        deck.setIsStarted(0);
//        deck.cardsList.clear();
    }

    @FXML
   private void showStartWindow(ActionEvent e) {
        // Optional.ofNullable(deck).ifPresent(d -> dataBase.saveDB(d));
        // Optional.ofNullable(deck).ifPresent(dataBase::saveDB);
        Optional<ButtonType> result = DialogsUtils.cancelCreateDeckDialog();
        if (result.get() == ButtonType.OK) {
            primaryStage.close();
            manager.showStart();
        }

    }

    @FXML
    private void chooseImage(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("wybierz obrazek");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        // fileChooser.getExtensionFilters().addAll(
        // new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        // new FileChooser.ExtensionFilter("All file", "*.*")
        // );
        // File file = new File(null);
        fileChooserImage = fileChooser.showOpenDialog(new Stage());
        if (fileChooserImage != null) {
            linkToImage = fileChooserImage.getAbsolutePath();
//            card.setImage(fileChooserImage.getAbsolutePath());
        }
    }

    
    @FXML
    private void nextCard(ActionEvent e) {
        btnPreviousCard.setDisable(false);
        viewTypeOfAward();

        setCardProperties();

        cardsList.add(card);
        cardCounter++;

        lblnrKarty.setText(Integer.toString(cardCounter));

        fileChooserImage = null;
        // TODO: uncomment when will you finish
        // txtNazwa.setText("");
        // txtOpis.setText("");

        if (cardCounter == 11) {
            endCreate();
        }
        card = new Card();
    }

    @FXML
    private void previousCard() {
        if (cardCounter == 2) {
            btnPreviousCard.setDisable(true);
        }
        cardCounter--;
        cardsList.remove(cardCounter - 1);
        // Card tempCard = new Card();
        Card tempCard = cardsList.get(cardsList.size() - 1);
        txtNazwa.setText(tempCard.getTitle());
        txtOpis.setText(tempCard.getDescription());
        lblnrKarty.setText(Integer.toString(cardCounter));
        viewTypeOfAward();
    }

    private void viewTypeOfAward() {
        if (cardCounter > 0 && cardCounter < 4)
            lblTyp.setText(Integer.toString(2));
        else
            lblTyp.setText(Integer.toString(3));
    }

    private void setCardProperties() {
        card.setTitle(txtNazwa.getText());
        card.setDescription(txtOpis.getText());
        if (fileChooserImage != null) {
            card.setImage(linkToImage);
        } else {
            card.setImage("default");
        }
        switch (cardCounter) {
        case 1:
            card.setType(1);
            break;
        case 2:
        case 3:
        case 4:
            card.setType(2);
            break;
        default:
            card.setType(3);
            break;
        }
    }

    private void endCreate() {

        addPropertiesToDeck();
        DeckModel deckModel = new DeckModel();
        deckModel.saveDeckInDataBase(deck);
        CardModel cardModel = new CardModel();
        cardsList.forEach(card -> cardModel.saveCardInDataBase(card));
//        cardModel.saveAllCardsInDataBase(cardsList);
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


}