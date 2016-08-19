package view;

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

public class DeckNameDialogController {
	private Stage dialogStage;
	private ViewManager manager; 
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

    @FXML
    void accept(ActionEvent event) {
    	String deckName = tfDeckName.getText();
    	if(deckName.isEmpty()){
    		tfDeckName.setBorder(new Border(new BorderStroke(Color.RED, 
    	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    	} else {
    	DBmanager.deck.setName(deckName);
    	dialogStage.close();
		DBmanager.deck.setHowManyCards(40);
		DBmanager.deck.setHowManySmallCards(6);
		DBmanager.deck.setHowManyMediumCards(3);
		DBmanager.deck.setIsStarted(1);
		DBmanager.amountOfDecks++;
// TODO: przeniesc gdzies do wybierania decku
//		 DBmanager.deck.createDeck();
		DBmanager.saveDB();
		manager.showStart();
    	System.out.println(deckName);
		}
    }

    @FXML
    void cancel(ActionEvent event) {
    	dialogStage.close();
    }
}
