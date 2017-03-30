package controller;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;

import java.util.Map;

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

    public void init(Map<String, Integer> mapOfDecks) {
        createComboBox(mapOfDecks);
        if (mapOfDecks.size() == 0) {
            btnDraw.setDisable(true);
            // btnDraw.setVisible(false);
        }
        // gdy jest utworzona
        else {
            System.out.println("rozpoczeta");
        }
    }

    private void createComboBox(Map<String, Integer> mapOfDecks) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        // Collection<String> listOfDecks = DBmanager.mapOfDecks.values();
        for (String deck : mapOfDecks.keySet()) {
            observableList.add(deck);
        }
        comboBoxOfDecks.setItems(observableList);
        // TODO: problem z powtarzajacymi sie nazwami
        // ObservableMapValue observableMap = (ObservableMapValue)
        // FXCollections.observableHashMap();
        // for (int i = 0; i < DBmanager.listOfDecks.length; i++) {
        // observableMap.put("key", DBmanager.listOfDecks[i]);
        // }
        // comboBoxOfDecks.setItem(observableMap.get());
    }

    @FXML
    void showAbout(ActionEvent e) {
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

    // @FXML
    // private void menuCreateDeck() {
    // manager.showCardCreator();
    // }
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
            manager.showDrawCard(deckName);
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
//        Stage stage = (Stage) primaryStage;
         boolean value = ((CheckMenuItem) actionEvent.getSource()).selectedProperty().get();
         primaryStage.setAlwaysOnTop(value);
     }
    @FXML
    private void closeApp(){
        primaryStage.close();
    }
    @FXML
    private void setImgDir(){
        
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

}
