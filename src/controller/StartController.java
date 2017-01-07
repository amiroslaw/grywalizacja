package controller;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DBmanager;
import view.DialogsUtils;

public class StartController {
    // DrawCardController sampleController = new DrawCardController();
//    ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");
    @FXML
    private Button btnCreate;
    @FXML
    private ComboBox<String> comboBoxOfDecks;
    @FXML
    private Button btnDraw;
    @FXML
    private Label info;
    private Stage primaryStage;
    private ViewManager manager;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }
    public void init(Map<String,Integer> mapOfDecks) {
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
    private void createComboBox(Map<String,Integer> mapOfDecks) {
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
    private void goToDrawCard() {
        String deckName = comboBoxOfDecks.getValue();
      
        if(deckName != null){
        manager.showDrawCard(deckName);
        } else {
            String inf = manager.bundle.getString("dialog.inf.chooseDeck");
            DialogsUtils.dialogInformation(inf);
        }
    }

    @FXML
    private void goToCreate() {
        manager.showCardCreator();
    }

    @FXML
    private void goToStart() {
        manager.showStart();
    }

    
}
