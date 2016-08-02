package view;

import java.io.IOException;

import application.Main;
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

	public ViewManager(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	public void showStart() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/Start.fxml"));
			VBox vboxStart = (VBox) loader.load();

			Scene scene = new Scene(vboxStart);
			primaryStage.setTitle("Grywalizacja ekran startowy");
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			StartController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDrawCard() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DrawCard.fxml"));
			BorderPane borderPane = (BorderPane) loader.load();

			Scene scene = new Scene(borderPane);
			primaryStage.setTitle("Grywalizacja- losu");
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			DrawCardController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showCardCreator() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/CardCreator.fxml"));
			GridPane cardCreatorLayout = (GridPane) loader.load();

			Scene scene = new Scene(cardCreatorLayout);
			primaryStage.setTitle("Grywalizacja-O tworzenie talii");
			primaryStage.setScene(scene);
			// TODO zamkniecie okienka i zapis db
			// primaryStage.setOnCloseRequest(e->controller.saveDB(null));

			CardCreatorController controller = loader.getController();
			controller.setPrimaryStage(this.primaryStage);
			controller.setManager(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void showAbout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/About.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("O programie");
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
	public void showDeckNameDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/DeckNameDialog.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nazwa talii");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			DeckNameDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(this);

			dialogStage.setResizable(false);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
