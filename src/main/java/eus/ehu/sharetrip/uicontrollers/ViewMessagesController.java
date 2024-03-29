package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.fxml.FXML;

public class ViewMessagesController implements Controller {

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public ViewMessagesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewMessages button is working");
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}