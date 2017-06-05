package controller;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;

import java.util.List;

import database.Deck;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DeckModel;
import view.DialogsUtils;

public class StartController {

    @FXML private Button btnDraw;
    @FXML private ComboBox<String> comboBoxOfDecks;
    @FXML private Label info;
    private Stage primaryStage;
    private ViewManager manager;
    private DeckModel deckModel;
    private List<Deck> deckList;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void init() {
        deckModel = new DeckModel();
        deckModel.getAllDecks();
        deckList = deckModel.getDeckList();
        if (deckList.size() == 0) {
            btnDraw.setDisable(true);
        } else {
            createComboBox(deckList);
        }
    }

    private void createComboBox(List<Deck> decks) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        decks.forEach(e -> observableList.add(e.getDeckName()));
        comboBoxOfDecks.setItems(observableList);
    }

    @FXML
    private void showAbout() {
        manager.showAbout();
    }

    @FXML
    private void showDeckManager() {
        manager.showDeckManager();
    }

    @FXML
    private void showCardCreator() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                manager.showCardCreator();
            }
        });

    }

    @FXML
    private void menuDrawCard() {
        showDrawCard();
    }

    @FXML
    private void btnDrawCard() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showDrawCard();
            }
        });
    }

    private void showDrawCard() {
        String deckName = comboBoxOfDecks.getValue();
        if (deckName != null) {
            Deck deck = deckList.stream().filter(e -> e.getDeckName().contains(deckName)).findFirst().orElse(null);
            manager.showDrawCard(deck.getId());
        } else {
            String inf = manager.bundle.getString("dialog.inf.chooseDeck");
            DialogsUtils.dialogInformation(inf);
        }
    }

    @FXML
    private void setCaspian() {
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
    }

    @FXML
    private void setModena() {
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);
    }

    @FXML
    private void setAlwaysOnTop(ActionEvent actionEvent) {
        boolean value = ((CheckMenuItem) actionEvent.getSource()).selectedProperty().get();
        primaryStage.setAlwaysOnTop(value);
    }

    @FXML
    private void closeApp() {
        primaryStage.close();
    }

    @FXML
    private void setImgDir() {

    }

}
