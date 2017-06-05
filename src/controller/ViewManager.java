package controller;

import java.io.IOException;
import java.util.ResourceBundle;

import application.Main;
import database.DbManager;
import database.Deck;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewManager {
    public final Stage primaryStage;
    ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");
    private static final String EDIT_CARDS_FXML = "/view/EditCards.fxml";
    private static final String DECK_MANAGER_FXML = "/view/DeckManager.fxml";
    private static final String START_FXML = "/view/Start.fxml";
    private static final String DRAW_CARD_FXML = "/view/DrawCard.fxml";
    private static final String ABOUT_FXML = "/view/About.fxml";
    private static final String CARD_CREATOR_FXML = "/view/CardCreator.fxml";

    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        DbManager.initDatabase();
    }

    public void showStart() {
        try {
            FXMLLoader loader = getLoader(START_FXML);
            VBox vboxStart = (VBox) loader.load();

            Scene scene = new Scene(vboxStart);
            primaryStage.setTitle(bundle.getString("title.start"));
            primaryStage.setScene(scene);

            StartController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setManager(this);
            controller.init();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDrawCard(int id) {
        try {
            FXMLLoader loader = getLoader(DRAW_CARD_FXML);
            BorderPane borderPane = (BorderPane) loader.load();

            Scene scene = new Scene(borderPane);
            primaryStage.setTitle(bundle.getString("title.draw"));
            primaryStage.setScene(scene);

            DrawCardController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setManager(this);
            controller.init(id);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showCardCreator() {
        try {
            FXMLLoader loader = getLoader(CARD_CREATOR_FXML);
            GridPane cardCreatorLayout = (GridPane) loader.load();

            Scene scene = new Scene(cardCreatorLayout);
            primaryStage.setTitle(bundle.getString("title.creator"));
            primaryStage.setScene(scene);

            CardCreatorController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setManager(this);
            controller.init();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAbout() {
        try {
            FXMLLoader loader = getLoader(ABOUT_FXML);
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

    public void showDeckManager() {
        try {
          
            FXMLLoader loader = getLoader(DECK_MANAGER_FXML);
            AnchorPane deckManager = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(bundle.getString("title.manager"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(deckManager);
            dialogStage.setScene(scene);

            DeckManagerController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setManager(this);

            dialogStage.setOnCloseRequest(e -> showStart());
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditCards(Deck deck) {
        try {
            FXMLLoader loader = getLoader(EDIT_CARDS_FXML);
            AnchorPane layout = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(bundle.getString("title.editor"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            EditCardsController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setManager(this);
            controller.init(deck);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxmlPath));
        loader.setResources(bundle);
        return loader;
    }
}
