package eus.ehu.sharetrip.uicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> roles;

}
