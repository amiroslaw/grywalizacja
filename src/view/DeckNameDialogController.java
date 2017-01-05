package view;

import java.util.HashMap;

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
import model.DBmanager;
import model.Deck;

public class DeckNameDialogController {
	private Stage dialogStage;
	private ViewManager manager; 
	private DBmanager dataBase;
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
    	if(deckName.isEmpty()){
    		tfDeckName.setBorder(new Border(new BorderStroke(Color.RED, 
    	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    	} else {
    	    deck.setName(deckName);
    	    deck.setID(amoutOfDeck);
    	    mapOfDecks.put(deckName, amoutOfDeck);

    	    deck.setHowManyCards(40);
    	    deck.setHowManySmallCards(6);
    	    deck.setHowManyMediumCards(3);
    	    deck.setIsStarted(1);
    	    amoutOfDeck ++;
		deck.createDeck();
		dataBase.saveDB(deck);
    	dialogStage.close();
    	System.out.println(deckName);
		}
    }

    @FXML
    void cancel(ActionEvent event) {
    	dialogStage.close();
    }
    public void init(Deck deck, HashMap<String, Integer> mapOfDecks, DBmanager dbManager) {
        this.deck = deck;   
        this.mapOfDecks = mapOfDecks;
        amoutOfDeck = mapOfDecks.size();
        dataBase = dbManager; 
     }

}
