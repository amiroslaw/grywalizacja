package view;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DBmanager;

public class StartController {
    // DrawCardController sampleController = new DrawCardController();
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
    private DBmanager dataBase;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }
    public void init(Map<String,Integer> mapOfDecks) {
//        this.dataBase = dataBase;
//        System.out.println("view constructior: " + dataBase.getMapOfDecks().get("a"));
//        System.out.println("amount " + dataBase.getAmountOfDecks());
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
    private void goToDrawCard() {
        String deckName = comboBoxOfDecks.getValue();
      
        if(deckName != null){
        manager.showDrawCard(deckName);
        // System.out.println(comboBoxOfDecks.getValue());
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
