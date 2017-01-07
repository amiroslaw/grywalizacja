package view;

import org.junit.Before;

import controller.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartControllerTest extends Application{

//    @Before
//    public void initialize(){
//        final Stage primaryStage = null;
//    ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle");
//    final String START_FXML = "/view/Start.fxml";
//    DBmanager dataBase = new DBmanager();
//
//    Deck deck = new Deck();
//        ViewManager viewManager = new ViewManager(primaryStage);
//    }
    @Before
    @Override
    public void start(Stage primaryStage) {
        ViewManager viewManager = new ViewManager(primaryStage);
        viewManager.showStart();
    }
    @Before
    public static void main(String[] args) {
        launch(args);

    }
  
    
}
