package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Locale;

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
    void viewMyBookings(ActionEvent event) {mainGUI.showScene("MyBookings");}

    /**
     * Sign out logic, just 3 lines, we do not need another SignOutController
     * @param actionEvent
     */
    @FXML
    void signOut(ActionEvent actionEvent) {mainGUI.showScene("Log Out");}

    @FXML
    void setLanguageEn(ActionEvent event) {
        Locale.setDefault(new Locale("en"));
        System.out.println("Locale: " + Locale.getDefault());
/*
        Double width  = mainGUI.getStage().getScene().getWidth();
        Double height = mainGUI.getStage().getScene().getHeight();
        Boolean fullScreen = mainGUI.getStage().fullScreenProperty().getValue();
*/
        changeStageLanguage();
    }

    @FXML
    void setLanguageEs(ActionEvent event) {
        Locale.setDefault(new Locale("es"));
        System.out.println("Locale: " + Locale.getDefault());
        /*
        Double width  = mainGUI.getStage().getScene().getWidth();
        Double height = mainGUI.getStage().getScene().getHeight();
        Boolean fullScreen = mainGUI.getStage().fullScreenProperty().getValue();
        */
        changeStageLanguage();
    }

    @FXML
    void setLanguageEus(ActionEvent event) {
        Locale.setDefault(new Locale("eus"));
        System.out.println("Locale: " + Locale.getDefault());
        /*
        Double width  = mainGUI.getStage().getScene().getWidth();
        Double height = mainGUI.getStage().getScene().getHeight();
        Boolean fullScreen = mainGUI.getStage().isFullScreen();
        */
        changeStageLanguage();
    }

    private void changeStageLanguage() {
        Double width  = mainGUI.getStage().getWidth();
        Double height = mainGUI.getStage().getHeight();
        Boolean fullScreen = mainGUI.getStage().isFullScreen();

        try {
            mainGUI.init(mainGUI.getStage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainGUI.showUpdate(mainGUI.getStage(), width, height, fullScreen);

        try {
            User u = mainGUI.getBusinessLogic().getCurrentUser();
            if (u != null) {
                mainGUI.setUserName(u.getUsername());
                mainGUI.setIsLoggedIn(true);
                mainGUI.showScene("Query Rides");
            }
        }catch (NullPointerException e){
            System.out.println("No user logged in");
        }

    }

    @FXML
    void initialize() {
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
