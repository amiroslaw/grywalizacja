package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AboutController implements Initializable {
    private Stage dialogStage;
    private ViewManager manager;
    @FXML
    private TextField tfTest;
    @FXML
    private Button btnTest;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
