package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ViewMessagesController implements Controller {

    @FXML
    public ListView chatListView;
    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public ViewMessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewMessages button is working");
        setMessages();
    }

    public void setMessages() {
        chatListView.getItems().clear();
        chatListView.getItems().addAll(businessLogic.getMessages().toString());
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}