package view;

import java.io.IOException;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBmanager;
import model.Instrument;

public class ViewManager {
	private static final String START_FXML = "/view/Start.fxml";
    public final Stage primaryStage;
	private String vs= "View String ";
	public String getStr(){
	    return vs;
	}
	public void setStr(String str){
	    vs = str;
	}
	private DBmanager dataBase = new DBmanager();
	ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");
	
	public ViewManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
		 dataBase.createDB();
	        dataBase.readListOfDecks();
	   
	}
	public void showStart() {
		try {
		    
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(START_FXML));
			loader.setResources(bundle);
			VBox vboxStart = (VBox) loader.load();

			Scene scene = new Scene(vboxStart);
			primaryStage.setTitle(bundle.getString("title.start"));
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			StartController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			controller.getDataBase(dataBase);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDrawCard(String deckName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DrawCard.fxml"));
			loader.setResources(bundle);
			BorderPane borderPane = (BorderPane) loader.load();

			Scene scene = new Scene(borderPane);
			primaryStage.setTitle(bundle.getString("title.draw"));
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			DrawCardController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			controller.init(deckName, dataBase);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showCardCreator() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CardCreator.fxml"));
			loader.setResources(bundle);
			GridPane cardCreatorLayout = (GridPane) loader.load();

			Scene scene = new Scene(cardCreatorLayout);
			primaryStage.setTitle(bundle.getString("title.creator"));
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			CardCreatorController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			controller.getDataBase(dataBase);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void showAbout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/About.fxml"));
			loader.setResources(bundle);
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(bundle.getString("title.about"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			AboutController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);

			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// test 
	   public void showAbout(Instrument inst) {
	        try {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("/view/About.fxml"));
	            loader.setResources(bundle);
	            AnchorPane pane = (AnchorPane) loader.load();

	            Stage dialogStage = new Stage();
	            dialogStage.setTitle(bundle.getString("title.about"));
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(pane);
	            dialogStage.setScene(scene);

	            AboutController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setManager(this);
	            controller.init(inst);
	            dialogStage.setResizable(false);
	            dialogStage.showAndWait();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	   
	public void showDeckNameDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DeckNameDialog.fxml"));
			loader.setResources(bundle);
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(bundle.getString("title.deck_name"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			DeckNameDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);
		    controller.getDataBase(dataBase);
			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
