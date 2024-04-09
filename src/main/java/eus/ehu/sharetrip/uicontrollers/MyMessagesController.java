package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MyMessagesController implements Controller{

    @FXML
    private TableView<Message> sentTableView;

    @FXML
    private TableView<Message> receivedTableView;

    @FXML
    private TableColumn<Message, String> receivedColumn;

    @FXML
    private TableColumn<Message, String> sentColumn;
    private MainGUI mainGUI;

    private BlFacade businessLogic;

    private ObservableList<Message> receivedMessages = FXCollections.observableArrayList();
    private ObservableList<Message> sentMessages = FXCollections.observableArrayList();


    public MyMessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewMessages button is working");
        receivedTableView.setItems(receivedMessages);
        sentTableView.setItems(sentMessages);
        setReceivedMessages();
        setSentMessages();
        System.out.println("Received messages: ");
    }

    private void configureColumns() {
        // Configurar las columnas según sea necesario
        receivedColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMessage());
        });
        sentColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMessage());
        });
    }


    private void setSentMessages() {
        sentMessages.setAll(businessLogic.getSentMessages(businessLogic.getCurrentUser()));
    }


    private void setReceivedMessages() {
        receivedMessages.setAll(businessLogic.getReceivedMessages(businessLogic.getCurrentUser()));
    }

    public void onShow() {
        setSentMessages();
        setReceivedMessages();
    }
    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
