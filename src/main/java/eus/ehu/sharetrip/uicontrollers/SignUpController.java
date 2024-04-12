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

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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

    private MainGUI mainGUI;


    public SignUpController(BlFacade bl) {
        this.bl = bl;
    }

    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
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
         * Check that the password is valid
         */
        if (!isValid(userPassword, confirmPassword)) {
            errorsLabel.setText("Passwd needs 8+ chars, uppercase, lowercase, digits, and special chars.");
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

        this.autoLogin(userName, userPassword);

        mainGUI.showScene("Query Rides");

    }

    private void autoLogin(String username, String password) {
        try {
            bl.login(username, password);
            mainGUI.setUserName(username);
            mainGUI.setIsLoggedIn(true);
        } catch (UnknownUser unknownUser) {
            System.out.println("Unknown user");
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Driver", "Traveler");
        roles.setItems(options);
    }

    public static boolean isValid(String passwordhere, String confirmhere) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        boolean flag = true;

        if (
                passwordhere.length() < 8 ||
                !specailCharPatten.matcher(passwordhere).find() ||
                !UpperCasePatten.matcher(passwordhere).find() ||
                !lowerCasePatten.matcher(passwordhere).find() ||
                !digitCasePatten.matcher(passwordhere).find()) {
            flag = false;
        }

        return flag;
    }

    }
