package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUIController implements Controller{

    @FXML
    public Button createRidesBtn;

    @FXML
    public Button logInBtn;

    @FXML
    public Button registerBtn;

    @FXML
    public Label userLbl;

    @FXML
    public Label userNameLbl;


    @FXML
    private Label selectOptionLbl;

    @FXML
    private Label lblDriver;


    @FXML
    private Button queryRidesBtn;

    @FXML
    private Button createRideBtn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    private BlFacade businessLogic;


    public MainGUIController(){};

    public MainGUIController(BlFacade blFacade){
        businessLogic = blFacade;
    }


    @FXML
    void initialize() {

    }


    @FXML
    void viewAlerts(ActionEvent event) {
        mainGUI.showScene("View Alert");
    }

    @FXML
    void viewMessages(ActionEvent event) {
        mainGUI.showScene("View Messages");
    }

    @FXML
    void viewFavorites(ActionEvent event) {
        mainGUI.showScene("View Favorites");
    }

    @FXML
    void queryRides(ActionEvent event) {
        mainGUI.showScene("Query Rides");
    }

    @FXML
    void createRide(ActionEvent event) {
        mainGUI.showScene("Create Ride");
    }

    @FXML
    void logIn(ActionEvent actionEvent) {
        mainGUI.showScene("Log in");
    }

    @FXML
    void register(ActionEvent actionEvent) {
        mainGUI.showScene("Register");
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
