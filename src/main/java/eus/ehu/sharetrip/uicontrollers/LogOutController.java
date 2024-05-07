package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LogOutController implements Controller {

    @FXML
    private Button video;
    @FXML
    private Button cancelBtn;

    @FXML
    private Button logOutBtn;

    private BlFacade businessLogic;

    private MainGUI mainGUI;



    public LogOutController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    public void cancelLogOut(ActionEvent actionEvent) {
        mainGUI.showScene("Query Rides");
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void confirmLogOut(ActionEvent actionEvent) {
        mainGUI.setIsLoggedIn(false);
        mainGUI.setUserName("");
        businessLogic.setCurrentUser(null);
        mainGUI.showScene("Log in");
    }

    public void chargeVideo(ActionEvent actionEvent) {
        mainGUI.showScene("Video");
    }
}
