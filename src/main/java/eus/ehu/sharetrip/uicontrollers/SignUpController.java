package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import eus.ehu.sharetrip.businessLogic.BlFacade;

public class SignUpController implements Controller{

    private final BlFacade bl;

    @FXML
    private Label errorsLabel;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private TextField username;


    public SignUpController(BlFacade bl) {
        this.bl = bl;
    }


    @FXML
    private void signUp() {
        String userEmail = email.getText();
        String userPassword = password.getText();
        String userRole = roles.getValue();
        String userName = username.getText();

        if (userEmail.isEmpty() || userPassword.isEmpty() || userRole == null || userName.isEmpty()) {
            errorsLabel.setText("Please fill in all the fields");
            return;
        }
        // Call the business logic to sign up the use
        try {
            bl.signup(userEmail, userName, userPassword, userRole);
            errorsLabel.setText("Registered successfully!");
        } catch (UnknownUser e) {
            throw new RuntimeException(e);
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
