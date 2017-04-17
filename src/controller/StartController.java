package controller;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DbManager;
import database.Deck;
import database.FillDBUtils;
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
    // DrawCardController sampleController = new DrawCardController();
    // ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");

    // @FXML
    // private MenuItem miDraw;
    // @FXML
    // private MenuItem miCreate;
    // @FXML
    // private Button btnCreate;
    @FXML
    private Button btnDraw;
    @FXML
    private ComboBox<String> comboBoxOfDecks;

    @FXML
    private Label info;
    private Stage primaryStage;
    private ViewManager manager;
    private DeckModel deckModel;
    private List<Deck> deckList;

    public void init(Map<String, Integer> mapOfDecks) {
        deckModel = new DeckModel();
        deckModel.getAllDecks();
        deckList = deckModel.getDeckList();
        if (deckList.size() == 0) {
            btnDraw.setDisable(true);
            // btnDraw.setVisible(false);
        }
        // gdy jest utworzona
        else {
            createComboBox(deckList);
            System.out.println("rozpoczeta");
        }
    }

    private void createComboBox(List<Deck> decks) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        decks.forEach(e -> observableList.add(e.getDeckName()));
        comboBoxOfDecks.setItems(observableList);
    }

    @FXML
    private void showAbout(ActionEvent e) {
        manager.showAbout();
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
        // Stage stage = (Stage) primaryStage;
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

}
