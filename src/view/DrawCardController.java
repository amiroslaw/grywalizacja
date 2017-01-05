package view;

import java.io.File;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.DBmanager;
import model.Deck;

/**
 * Kontroler do losowania z metodą {@link #readDeckInfo()}
 * 
 * @author miro
 *
 */
public class DrawCardController {

    private ViewManager manager;
    private Stage primaryStage;
    private Label labAbout = new Label("twórca Arkadiusz Mirosław");
    private Image award1 = new Image("img/award1.png");
    private Image award2 = new Image("img/award2.png");
    private Image award3 = new Image("img/award3.png");
    private Image award4 = new Image("img/cow.png");
    // private Image award4 = new Image("file:/home/miro/img/dum.jpg");
    // private Image award4 = new Image("file:/home/img/cpgoesdqxww.jpg");
    @FXML
    private MenuItem menuStart;
    @FXML
    private ImageView obrazek;
    @FXML
    private Button zamknij;
    @FXML
    private Button los;
    @FXML
    private Label wylosowane;
    @FXML
    private Label pozostalo;
    @FXML
    private Label pozostaloSrednich;
    @FXML
    private Label pozostaloMalych;

    private File imageFile;
    private String deckName;
    private HashMap<String, Integer> mapOfDecks = new HashMap<>();
    DBmanager dBmanager = new DBmanager();
    private Deck deck;
        private int howManyCards;
        private int howManySmallCards;
        private int howManyMediumCards;
    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * sprawdza czy takia została zaczęta czy nie, wywołuje odpowiednie metody i
     * wyświtla ilość kart
     */

    @FXML
    void showAbout(ActionEvent e) {
        manager.showAbout();
    }

    @FXML
    void showStart(ActionEvent e) {
        manager.showStart();

    }

    @FXML
    void saveDB(ActionEvent event) {
        dBmanager.saveDB(deck);
    }

    /**
     * sprawdza czy jest pusta talia sprawdza jakiej kategorii jest nagroda i
     * wyświtla odpowiedni obrazek aktualizuje wyświtlanie ilości kart
     */
    @FXML
    void drawCard(ActionEvent event) {
        if (deck.cardsList.size() == 0) {
            deck.setIsStarted(3);
            mapOfDecks.remove(deckName);
            // DBmanager.saveDB();
            menuStart.setDisable(false);
            wylosowane.setText("gratuluję zakończyłeś talię");
        } else {
            deck.setIsStarted(1);
            String teskt = deck.cardsList.get(0).toString();
            wylosowane.setText(teskt);
            deck.setHowManyCards(howManyCards -- );
            checkTypeAndSetImage();

            pozostalo.setText(Integer.toString(howManyCards));
            pozostaloSrednich.setText(Integer.toString(howManyMediumCards));
            pozostaloMalych.setText(Integer.toString(howManySmallCards));

            deck.cardsList.remove(0);
        }

    }

    private void checkTypeAndSetImage() {
        int typeOfCard = deck.cardsList.get(0).getType();
        switch (typeOfCard) {
        case 1:
            if (deck.cardsList.get(0).getImage().equals("default")) {
                obrazek.setImage(award1);
            } else {
                imageFile = new File(deck.cardsList.get(0).getImage());
                obrazek.setImage(new Image(imageFile.toURI().toString()));
            }
            break;
        case 2:
            deck.setHowManyMediumCards(howManyMediumCards --);
            if (deck.cardsList.get(0).getImage().equals("default")) {
                obrazek.setImage(award2);
            } else {
                imageFile = new File(deck.cardsList.get(0).getImage());
                obrazek.setImage(new Image(imageFile.toURI().toString()));
            }
            break;
        case 3:
            deck.setHowManySmallCards(howManySmallCards --);
            if (deck.cardsList.get(0).getImage().equals("default")) {
                obrazek.setImage(award3);
            } else {
                imageFile = new File(deck.cardsList.get(0).getImage());
                obrazek.setImage(new Image(imageFile.toURI().toString()));
            }
            break;
        default:
            obrazek.setImage(award4);
            break;
        }
    }

    public void init(Deck deck) {
        this.deck = deck;
        System.out.println("deckName in DrawCard");

        menuStart.setDisable(true);
        obrazek.setImage(award4);
        
        howManyCards = deck.getHowManyCards();
        howManyMediumCards = deck.getHowManyMediumCards();
        howManySmallCards = deck.getHowManySmallCards();
        
        pozostalo.setText(Integer.toString(howManyCards));
        pozostaloSrednich.setText(Integer.toString(howManyMediumCards));
        pozostaloMalych.setText(Integer.toString(howManySmallCards));

    }

}
