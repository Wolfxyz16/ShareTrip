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

    private Window mainWin, createRideWin, queryRidesWin, loginWin, registerWin, favoriteOverviewWin, chatOverviewWin, alertOverviewWin;

    class Window {
        Controller c;
        Parent ui;
    }

    private Window load(String fxml) {
        /**
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource(fxml) );
            Parent ui = loader.load();
            Controller controller = loader.getController();

            Window window = new Window();
            window.c = controller;
            window.ui = ui;
            return window;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         **/
        try {
            FXMLLoader loader = new FXMLLoader( MainGUI.class.getResource(fxml), ResourceBundle.getBundle("Etiquetas", Locale.getDefault() ));
            loader.setControllerFactory( controllerClass -> {
                try {
                    return controllerClass
                            .getConstructor(BlFacade.class)
                            .newInstance(businessLogic);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent ui = loader.load();
            Controller controller = loader.getController();

            Window window = new Window();
            window.c = controller;
            window.ui = ui;
            return window;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private BorderPane mainWrapper;

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
    void viewAlerts(ActionEvent event) {
        showScene("View Alert");
    }

    @FXML
    void viewMessages(ActionEvent event) {
        showScene("View Messages");
    }

    @FXML
    void viewFavorites(ActionEvent event) {
        showScene("View Favorites");
    }

    @FXML
    void queryRides(ActionEvent event) {
        showScene("Query Rides");
    }

    @FXML
    void createRide(ActionEvent event) {
        showScene("Create Ride");
    }

    @FXML
    void logIn(ActionEvent actionEvent) {
        showScene("Log in");
    }

    @FXML
    void register(ActionEvent actionEvent) {
        showScene("Register");
    }


    @FXML
    void initialize() {


        createRideWin = load("CreateRide.fxml");
        queryRidesWin = load("QueryRides.fxml");
        loginWin = load("SignIn.fxml");
        registerWin = load("SignUp.fxml");
        favoriteOverviewWin = load("FavoriteOverview.fxml");
        chatOverviewWin = load("ChatOverview.fxml");
        alertOverviewWin = load("AlertOverview.fxml");



    }

    private void showScene(String scene) {
        switch (scene) {

            case "View Alert" -> mainWrapper.setCenter(alertOverviewWin.ui);
            case "View Messages" -> mainWrapper.setCenter(chatOverviewWin.ui);
            case "View Favorites" -> mainWrapper.setCenter(favoriteOverviewWin.ui);
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Log in" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
