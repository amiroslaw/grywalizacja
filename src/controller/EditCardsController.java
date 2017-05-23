package controller;

import java.io.File;

import database.Card;
import database.Deck;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.CardFx;
import model.CardModel;
import view.DialogsUtils;

public class EditCardsController {
    private Stage primaryStage;
    private ViewManager manager;
    @FXML
    private TextField titleTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private Button imageBtn;
    @FXML
    private Button btnAddCard;
    @FXML
    private TableView<CardFx> cardsTableView;
    @FXML
    private ComboBox<Integer> typeCB;
    @FXML
    // private TableColumn<CardFx, Number> typeColumn; //number instead Integer
    private TableColumn<CardFx, Integer> typeColumn;
    @FXML
    private TableColumn<CardFx, String> nameColumn;
    @FXML
    private TableColumn<CardFx, String> descriptionColumn;
    @FXML
    private TableColumn<CardFx, String> imageColumn;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem copyMenuItem;
    private SelectionModel<CardFx> selected;

    private CardModel cardModel;
    private Deck deck;
    private File fileChooserImage;
    // private ObservableValue<? extends String> imageLink;
    private StringProperty imageLink = new SimpleStringProperty();

    public void init(Deck deck) {
        fileComboBox();
        this.deck = deck;
        cardModel = new CardModel();
        cardModel.init(deck);
        bindings();
        bindingsTableView();
    }

    private void fileComboBox() {
        // ObservableList<String> options = FXCollections.observableArrayList(
        // "1", "2", "3");
        ObservableList<Integer> options = FXCollections.observableArrayList(1, 2, 3);
        typeCB.setItems(options);
    }

    private void bindingsTableView() {
        cardsTableView.setItems(this.cardModel.getCardFxObservableList());

        // this.typeColumn.setCellValueFactory(cellData ->
        // cellData.getValue().getType());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().getImage());

        // this.typeColumn.setCellFactory(TextFieldTableCell.<CardFx,
        // Number>forTableColumn(new NumberStringConverter()));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        imageColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        cardsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cardModel.setCardFxObjectPropertyEdit(newValue);
        });
    }

    private void bindings() {
        cardModel.getCardFxObjectProperty().get().getTitle().bind(this.titleTF.textProperty());
        cardModel.getCardFxObjectProperty().get().getDescription().bind(this.descriptionTF.textProperty());
        cardModel.getCardFxObjectProperty().get().getType().bind(this.typeCB.valueProperty());
        cardModel.getCardFxObjectProperty().get().getImage().bind(imageLink);
        // typeTF.textProperty().bind(this.cardModel.getCardFxObjectProperty().get().getType().asString());

        btnAddCard.disableProperty()
                .bind(this.titleTF.textProperty().isEmpty().or(this.typeCB.valueProperty().isNull()));
        deleteMenuItem.disableProperty()
                .bind(this.cardsTableView.getSelectionModel().selectedItemProperty().isNull());
        copyMenuItem.disableProperty()
                .bind(this.cardsTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setManager(ViewManager manager) {
        this.manager = manager;
    }

    @FXML
    private void addCard() {
        try {
            this.cardModel.saveCard();
        } catch (RuntimeException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        titleTF.clear();
        descriptionTF.clear();
        typeCB.getItems().clear();
    }

    @FXML
    private void deleteCard() {
        try {
            this.cardModel.deleteCard();
        } catch (RuntimeException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    @FXML
    private void copyCard() {
        CardFx selected = cardsTableView.getSelectionModel().getSelectedItem();
        Card card = new Card();
        card.setTitle(selected.getTitleString());
        card.setDescription(selected.getDescriptionString());
        card.setImage(selected.getImageString());
        card.setType(selected.getTypeInt());
        card.setDeck(deck);
        Card duplicateCart = new Card(card);
        cardModel.saveCardInDataBase(card);
        cardModel.init(deck);
       
    }

    @FXML
    private void onEditDescription(TableColumn.CellEditEvent<CardFx, String> cellEdit) {
        cardModel.getCardFxPropertyEdit().setDescription(cellEdit.getNewValue());
        updateInDatabase();
    }

    @FXML
    private void onEditImage(TableColumn.CellEditEvent<CardFx, String> cellEdit) {
        cardModel.getCardFxPropertyEdit().setImage(cellEdit.getNewValue());
        updateInDatabase();
    }

    @FXML
    private void onEditName(TableColumn.CellEditEvent<CardFx, String> cellEdit) {
        cardModel.getCardFxPropertyEdit().setTitle(cellEdit.getNewValue());
        updateInDatabase();

    }

    @FXML
    private void onEditType(TableColumn.CellEditEvent<CardFx, Integer> cellEdit) {
        cardModel.getCardFxPropertyEdit().setType(cellEdit.getNewValue());
        updateInDatabase();

    }

    @FXML
    private void getImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooserImage = fileChooser.showOpenDialog(new Stage());
        if (fileChooserImage != null) {
            imageLink.set(fileChooserImage.getAbsolutePath());
        }
    }

    private void updateInDatabase() {
        try {
            cardModel.saveCardEdit();
        } catch (RuntimeException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}
