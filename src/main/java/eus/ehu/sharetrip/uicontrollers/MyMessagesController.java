package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MyMessagesController implements Controller{

    // RECEIVED TABLE
    @FXML
    private TableColumn<Message, String> receivedMessageTextCol;

    @FXML
    private TableColumn<Message, User> receivedSenderNameCol;

    @FXML
    private TableView<Message> receivedTableView;

    //SENT table
    @FXML
    private TableColumn<Message, User> recipientNameCol;

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
        recipientNameCol.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        receivedMessageTextCol.setCellValueFactory(new PropertyValueFactory<>("messageText"));
        receivedSenderNameCol.setCellValueFactory(new PropertyValueFactory<>("sender"));

        receivedMessages = FXCollections.observableArrayList();
        sentMessages = FXCollections.observableArrayList();

        //receivedMessages = (ObservableList<Message>) businessLogic.getReceivedMessages(businessLogic.getCurrentUser());
        //sentMessages = (ObservableList<Message>) businessLogic.getSentMessages(businessLogic.getCurrentUser());

        receivedTableView.setItems(receivedMessages);
        sentTableView.setItems(sentMessages);
    }


    private void setSentMessages() {
    }


    private void setReceivedMessages() {
        receivedMessages.setAll(businessLogic.getReceivedMessages(businessLogic.getCurrentUser()));
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
