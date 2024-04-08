package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.exceptions.CityAlreadyExistException;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import eus.ehu.sharetrip.businessLogic.BlFacade;

public class CreateCityController implements Controller {

    private BlFacade businessLogic;
    private MainGUI mainGUI;

    public CreateCityController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    private TextField newCity;

    @FXML
    private Label newCityStatusMessage;

    @FXML
    void createCityClick(ActionEvent event){
        String city = newCity.getText();
        try {
            businessLogic.createCity(city);
            newCityStatusMessage.setText("City created successfully");
        } catch (CityAlreadyExistException e) {
            newCityStatusMessage.setText("City already exists");
        }


    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
