package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Controller {

    private final BlFacade bl;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginStatus;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private MainGUI mainGUI;

    public SignInController(BlFacade blFacade){
        bl = blFacade;
    }

    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    void onClick(ActionEvent event) {

        if (login.getText() == null
                || password.getText() == null)
            return;
        try {
            bl.login(login.getText(), password.getText());
            loginStatus.setText("Logged in");
            loginStatus.getStyleClass().setAll("label", "lbl-success");
        } catch (UnknownUser unknownUser) {
            loginStatus.setText("Unknown user");
            loginStatus.getStyleClass().setAll("label", "lbl-danger");
            System.out.println("Unknown user");

        }

    }

    @FXML
    void initialize() {
    }

}
