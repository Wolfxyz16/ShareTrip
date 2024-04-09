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

    public Button messagesBtn;
    
    @FXML
    private Button alertsBtn;

    @FXML
    private Button createCityBtn;

    @FXML
    private Button favoritesBtn;

    @FXML
    public Button createRidesBtn;

    @FXML
    public Button logInBtn;

    @FXML
    public Button registerBtn;

    @FXML
    public Button signOutBtn;

    @FXML
    public Label userLbl;

    @FXML
    private Label selectOptionLbl;

    @FXML
    private Label welcome;


    @FXML
    private Button queryRidesBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    private boolean isLoggedIn;


    //public MainGUIController(){};

    public MainGUIController(BlFacade blFacade){
        businessLogic = blFacade;
    }


    @FXML
    private BorderPane mainWrapper;


    @FXML
    void initialize() {

    }


    @FXML
    void viewAlerts(ActionEvent event) {
        mainGUI.showScene("View Alert");
    }

    @FXML
    void viewMessages(ActionEvent event) {
        mainGUI.showScene("Message Overview");
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

    @FXML
    void createCity(ActionEvent event) {
        mainGUI.showScene("Create City");
    }

    /**
     * Sign out logic, just 3 lines, we do not need another SignOutController
     * @param actionEvent
     */
    @FXML
    void signOut(ActionEvent actionEvent) {
        mainGUI.setIsLoggedIn(false);
        mainGUI.setUserName("");
        businessLogic.setCurrentUser(null);
        mainGUI.showScene("Query Ride");
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public BorderPane getMainWrapper() {
        return mainWrapper;
    }

    public void setUserName(String label) {
        userLbl.setText(label);
    }

    public void initializeButtonVisibility() {
        logInBtn.setVisible(true);
        registerBtn.setVisible(true);
        queryRidesBtn.setVisible(true);
        createRidesBtn.setVisible(false);
        alertsBtn.setVisible(false);
        favoritesBtn.setVisible(false);
        messagesBtn.setVisible(false);
        signOutBtn.setVisible(false);
        welcome.setVisible(false);
        createCityBtn.setVisible(false);
    }
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        updateButtonVisibility();
    }
    private void updateButtonVisibility() {
        logInBtn.setVisible(!isLoggedIn);
        registerBtn.setVisible(!isLoggedIn);
        //queryRidesBtn.setVisible(isLoggedIn);
        createRidesBtn.setVisible(isLoggedIn);
        alertsBtn.setVisible(isLoggedIn);
        favoritesBtn.setVisible(isLoggedIn);
        messagesBtn.setVisible(isLoggedIn);
        signOutBtn.setVisible(isLoggedIn);
        userLbl.setVisible(isLoggedIn);
        welcome.setVisible(isLoggedIn);
        createCityBtn.setVisible(isLoggedIn);
    }
}
