package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainGUIController implements Controller{

    @FXML
    private Button alertsBtn;

    @FXML
    private Button bookingRequestsBtn;

    @FXML
    private Button myBookingsBtn;

    @FXML
    private Button createCityBtn;

    @FXML
    private Button createRidesBtn;

    @FXML
    private Button favoritesBtn;

    @FXML
    private Button logInBtn;

    @FXML
    private BorderPane mainWrapper;

    @FXML
    private Button messagesBtn;

    @FXML
    private Button queryRidesBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private Label userLbl;

    @FXML
    private Label welcome;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    private boolean isLoggedIn;

    public MainGUIController(BlFacade blFacade){
        businessLogic = blFacade;
    }


    @FXML
    void initialize() {}

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
    void logIn() {
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

    @FXML
    void viewBookingRequests(ActionEvent event) {mainGUI.showScene("BookingRequests");}

    @FXML
    void viewMyBookings(ActionEvent event) {
        mainGUI.showScene("MyBookings");}

    /**
     * Sign out logic, just 3 lines, we do not need another SignOutController
     * @param actionEvent
     */
    @FXML
    void signOut(ActionEvent actionEvent) {
        mainGUI.setIsLoggedIn(false);
        mainGUI.setUserName("");
        businessLogic.setCurrentUser(null);
        this.logIn();
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
        myBookingsBtn.setVisible(false);
        bookingRequestsBtn.setVisible(false);
    }
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        updateButtonVisibilityDependingOnUserType();
    }
    private void updateButtonVisibilityDependingOnUserType() {
        //Deactivate login and register buttons
        logInBtn.setVisible(!isLoggedIn);
        registerBtn.setVisible(!isLoggedIn);

        //Different buttons will be visible for Drivers or for Travelers
        if (businessLogic.getCurrentUserType().equals("TRAVELER")) {
            myBookingsBtn.setVisible(isLoggedIn);

        } else if (businessLogic.getCurrentUserType().equals("DRIVER")) {
            createRidesBtn.setVisible(isLoggedIn);
            createCityBtn.setVisible(isLoggedIn);
            bookingRequestsBtn.setVisible(isLoggedIn);
        }

        //These will be shown for both drivers and travelers
        alertsBtn.setVisible(isLoggedIn);
        favoritesBtn.setVisible(isLoggedIn);
        messagesBtn.setVisible(isLoggedIn);
        signOutBtn.setVisible(isLoggedIn);
        userLbl.setVisible(isLoggedIn);
        welcome.setVisible(isLoggedIn);
    }
}
