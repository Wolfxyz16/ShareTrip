package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.exceptions.UserAlreadyExistException;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import eus.ehu.sharetrip.businessLogic.BlFacade;

import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class SignUpController implements Controller{

    private final BlFacade bl;

    @FXML
    private Label errorsLabel;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPasswd;

    @FXML
    private ComboBox<String> roles;


    public SignUpController(BlFacade bl) {
        this.bl = bl;
    }


    @FXML
    private void signUp() {
        String userEmail = email.getText();
        String userPassword = password.getText();
        String userRole = roles.getValue();
        String userName = username.getText();
        String confirmPassword = confirmPasswd.getText();

        /**
         * Checks that there are not empty fields
         */
        if (userEmail.isEmpty() || userPassword.isEmpty() || userRole == null || userName.isEmpty()) {
            errorsLabel.setText("ERROR! Please fill in all the fields");
            errorsLabel.getStyleClass().setAll("label", "lbl-danger");
            return;
        }

        /**
         * We check that both passwords are the same
         */
        if (!userPassword.equals(confirmPassword)) {
            errorsLabel.setText("ERROR! Passwords do not match");
            errorsLabel.getStyleClass().setAll("label", "lbl-danger");
            return;
        }

        /**
         * Check that the email has a valid format
         */
        if (!userEmail.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") ) {
            errorsLabel.setText("ERROR! Invalid email format");
            errorsLabel.getStyleClass().setAll("label", "lbl-danger");
            return;
        }

        // Call the business logic to sign up the use
        try {
            bl.signup(userEmail, userName, userPassword, userRole);
            errorsLabel.setText("Registered successfully!");
            errorsLabel.getStyleClass().setAll("label", "lbl-success");
        } catch (UnknownUser e) {
            throw new RuntimeException(e);
        } catch (UserAlreadyExistException e) {
            errorsLabel.setText("ERROR! User already exists.");
            errorsLabel.getStyleClass().setAll("label", "lbl-danger");
        }

    }

    @FXML
    void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Driver", "Traveler");
        roles.setItems(options);
    }



    public void setMainApp(MainGUI mainGUI) {

    }
}
