package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class MyMessagesController implements Controller{

    @FXML
    private ListView receivedListView;

    @FXML
    private ListView sentListView;

    @FXML
    public Button backBtn;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public MyMessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewMessages button is working");
        setReceivedMessages();
        setSentMessages();
    }

    private void setSentMessages() {
        sentListView.getItems().clear();
        sentListView.getItems().addAll(businessLogic.getSentMessages(businessLogic.getCurrentUser()).toString());
    }

    public void setReceivedMessages() {
        receivedListView.getItems().clear();
        receivedListView.getItems().addAll(businessLogic.getReceivedMessages(businessLogic.getCurrentUser()).toString());
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void backToMessageController(ActionEvent actionEvent) {
        mainGUI.showScene("Message Overview");
    }
}
