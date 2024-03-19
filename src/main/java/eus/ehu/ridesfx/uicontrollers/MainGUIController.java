package eus.ehu.ridesfx.uicontrollers;

import eus.ehu.ridesfx.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import eus.ehu.ridesfx.ui.MainGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class MainGUIController implements Controller{

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
    void queryRides(ActionEvent event) {
        mainGUI.showQueryRides();
    }

    @FXML
    void createRide(ActionEvent event) {
        mainGUI.showCreateRide();
    }


    @FXML
    void initialize() {

            // set current driver name
            lblDriver.setText(businessLogic.getCurrentDriver().getName());
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
