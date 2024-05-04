package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.exceptions.UnknownUser;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
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
        loginStatus.setVisible(true);
        // Checks that no field is null
        if (login.getText().isEmpty() || password.getText().isEmpty()){
            String error = ResourceBundle.getBundle("Etiquetas").getString("ErrorEmptyFields");
            loginStatus.setText(error);
            loginStatus.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return;
        }
        String username = login.getText();
        String pass = password.getText();
            try {
                    String hashed = bl.getHashedPassword(username);
                    if (BCrypt.checkpw(pass, hashed)) {
                        bl.login(username, hashed);
                        mainGUI.setUserName(username);
                        mainGUI.setUserName(username);
                        mainGUI.setIsLoggedIn(true);
                        String logged = ResourceBundle.getBundle("Etiquetas").getString("LogedIn");
                        loginStatus.setText(logged);
                        loginStatus.getStyleClass().setAll("label", "lbl-success");
                        dissapearLabel();
                        mainGUI.showScene("Query Rides");
                    }
                    else {

                    String error = ResourceBundle.getBundle("Etiquetas").getString("WrongPassword");
                    loginStatus.setText(error);
                    loginStatus.getStyleClass().setAll("label", "lbl-danger");
                    dissapearLabel();
                }

            } catch (Exception e) {
                String error = ResourceBundle.getBundle("Etiquetas").getString("UnknownUser");
                loginStatus.setText(error);
                loginStatus.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
            }
        }


    @FXML
    void initialize() {
    }

    public void clearFields() {
        login.setText("");
        password.setText("");
        loginStatus.setText("");
        loginStatus.getStyleClass().setAll("label");
    }

    private void dissapearLabel() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                loginStatus.setVisible(false);
            });
        }).start();
    }
}
