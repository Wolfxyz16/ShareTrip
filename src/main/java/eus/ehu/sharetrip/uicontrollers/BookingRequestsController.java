package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.fxml.FXML;

public class BookingRequestsController implements Controller  {

    private BlFacade businessLogic;

    private MainGUI mainGUI;


    public BookingRequestsController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    void initialize() {}

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
