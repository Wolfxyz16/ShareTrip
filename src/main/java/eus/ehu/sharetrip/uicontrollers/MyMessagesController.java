package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class MyMessagesController implements Controller{

    // RECEIVED TABLE
    @FXML
    private TableColumn<Message, String> receivedMessageTextCol;

    @FXML
    private TableColumn<Message, String> receivedSenderNameCol;

    @FXML
    private ListView sentListView;

    @FXML
    public Button backBtn;

    @FXML
    private TableView<Message> receivedTableView;

    @FXML
    private TableColumn<Message, String> recipientNameCol;

    @FXML
    private TableColumn<Message, String> sentMessageTextCol;

    @FXML
    private TableView<Message> sentTableView;

    private ObservableList<Message> receivedMessages;

    private ObservableList<Message> sentMessages;

    private MainGUI mainGUI;

    private BlFacade businessLogic;


    public MyMessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewMessages button is working");
        sentMessageTextCol.setCellValueFactory(new PropertyValueFactory<>("messageText"));
        recipientNameCol.setCellValueFactory(new PropertyValueFactory<>("recipientName"));

        receivedMessageTextCol.setCellValueFactory(new PropertyValueFactory<>("messageText"));
        receivedSenderNameCol.setCellValueFactory(new PropertyValueFactory<>("senderName"));

        receivedMessages = FXCollections.observableArrayList();
        sentMessages = FXCollections.observableArrayList();
        receivedTableView.setItems(receivedMessages);
        sentTableView.setItems(sentMessages);
    }



    public void updateTables() {
        receivedMessages.clear();
        sentMessages.clear();
        User currentUser = businessLogic.getCurrentUser();
        List<Message> receivedMessages = businessLogic.getReceivedMessages(currentUser);
        List<Message> sentMessages = businessLogic.getSentMessages(currentUser);
        this.receivedMessages.addAll(receivedMessages);
        this.sentMessages.addAll(sentMessages);
        }



    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void backToMessageController(ActionEvent actionEvent) {
        mainGUI.showScene("Message Overview");
    }

    public void sendAlertEmail(City departCity, City arrivalCity, Date date, int numSeats) {
        Message message1 = new Message.Builder()
                .messageText("Hello")
                .sender(businessLogic.getSystemUser())
                .receiver(traveler2)
                .build();
    }
}
