package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtils {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");

    public static void dialogInformation(String information) {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        // informationAlert.setTitle(bundle.getString("dialog.inf.chooseDeck"));
        // informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setHeaderText(information);
        // informationAlert.setContentText(information);
        informationAlert.showAndWait();
    }

    public static Optional<ButtonType> cancelCreateDeckDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString("dialog.cancel_title"));
        confirmationDialog.setHeaderText(bundle.getString("dialog.cancel_header"));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static Optional<ButtonType> confirmationDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString("exit.title"));
        confirmationDialog.setHeaderText(bundle.getString("exit.header"));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static void errorDialog(String error) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("exit.title"));
        errorAlert.setHeaderText(bundle.getString("exit.header"));

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        errorAlert.showAndWait();

    }

    public static Optional<String> inputText() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("title.deck_name"));
        dialog.setHeaderText(bundle.getString("dialog.createDeck.header"));
        // dialog.setContentText(bundle.getString("dialog.createDeck.content"));

         Optional<String> result = dialog.showAndWait();
        
        return result;
    }
    public static String getImagePathDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("creator.choice_image"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("img", "*.jpg", "*JPG", "*.jpeg","*.JPEG", "PNG", "*.png", "*.PNG"),
                new FileChooser.ExtensionFilter("All file", "*.*"));
        File fileChooserImage = fileChooser.showOpenDialog(new Stage());
        return fileChooserImage != null ? fileChooserImage.getAbsolutePath() : "";
    }
}
