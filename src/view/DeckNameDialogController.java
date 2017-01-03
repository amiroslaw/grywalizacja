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
	private DBmanager dataBase;
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
    	    dataBase.getDeck().setName(deckName);
    	    dataBase.getDeck().setID(dataBase.getAmountOfDecks());
    	    dataBase.getMapOfDecks().put(deckName, dataBase.getAmountOfDecks());

    	    dataBase.getDeck().setHowManyCards(40);
    	    dataBase.getDeck().setHowManySmallCards(6);
    	    dataBase.getDeck().setHowManyMediumCards(3);
    	    dataBase.getDeck().setIsStarted(1);
    	    dataBase.setAmountOfDecks(dataBase.getAmountOfDecks()+1);
		dataBase.getDeck().createDeck();
		dataBase.saveDB();
    	dialogStage.close();
    	System.out.println(deckName);
		}
    }

    @FXML
    void cancel(ActionEvent event) {
    	dialogStage.close();
    }
    public void getDataBase(DBmanager dataBase) {
        this.dataBase = dataBase;  
     }

}
