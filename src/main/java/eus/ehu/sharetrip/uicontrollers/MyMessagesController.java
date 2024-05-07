package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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

        // Configurar la columna de mensajes enviados
        sentMessageTextCol.setCellValueFactory(new PropertyValueFactory<>("messageText"));
        recipientNameCol.setCellValueFactory(new PropertyValueFactory<>("recipientName"));

        // Configurar la columna de mensajes recibidos
        receivedMessageTextCol.setCellValueFactory(new PropertyValueFactory<>("messageText"));
        receivedSenderNameCol.setCellValueFactory(new PropertyValueFactory<>("senderName"));

        // Crear una función de celda personalizada para mostrar la vista previa emergente del texto completo
        sentMessageTextCol.setCellFactory(column -> {
            return new TableCell<Message, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setTooltip(null);
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip(item);
                        setTooltip(tooltip);
                    }
                }
            };
        });
        receivedMessageTextCol.setCellFactory(column -> {
            return new TableCell<Message, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setTooltip(null); // Eliminar la vista previa emergente si la celda está vacía
                    } else {
                        setText(item);
                        Tooltip tooltip = new Tooltip(item); // Crear una vista previa emergente con el texto completo
                        setTooltip(tooltip); // Asignar la vista previa emergente a la celda
                    }
                }
            };
        });

        // Configurar las listas observables y asignarlas a las tablas
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

    public void sendAlertEmail(User sentTo, City departCity, City arrivalCity, Date date, int numSeats) {
        Message message1 = new Message.Builder()
                .messageText("A new ride from " + departCity + " to " + arrivalCity + " that matches your alert has been created!")
                .sender(businessLogic.getSystemUser())
                .receiver(sentTo)
                .build();
        //this.receivedMessages.add(message1);
        businessLogic.saveMessage(message1);
        updateTables();
    }
}
