package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MessagesController implements Controller {

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public MessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("Messages button is working");
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


    @FXML
    void sendMessage(ActionEvent event) {
        mainGUI.showScene("Send message");
    }

    @FXML
    void viewMessages(ActionEvent event) {
        mainGUI.showScene("View messages");
    }


}