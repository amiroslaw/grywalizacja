package controller;

import java.util.HashMap;

import database.Deck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DeckNameDialogController {
    private Stage dialogStage;
    private ViewManager manager;
    private int amoutOfDeck;
    private HashMap<String, Integer> mapOfDecks = new HashMap<>();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    @FXML
    private TextField tfDeckName;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;
    private Deck deck;

    @FXML
    void accept(ActionEvent event) {
        String deckName = tfDeckName.getText();
        if (deckName.isEmpty()) {
            tfDeckName.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else {
            deck.setDeckName(deckName);
            deck.setId(amoutOfDeck);
            mapOfDecks.put(deckName, amoutOfDeck);

            deck.setIsStarted(1);
            amoutOfDeck++;

            dialogStage.close();
            System.out.println(deckName);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        dialogStage.close();
    }

}
