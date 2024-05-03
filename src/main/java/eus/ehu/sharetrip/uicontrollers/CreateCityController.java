package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.exceptions.CityAlreadyExistException;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import eus.ehu.sharetrip.businessLogic.BlFacade;

import java.util.Locale;
import java.util.ResourceBundle;

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
        newCityStatusMessage.setVisible(true);

        if (city.isEmpty()) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("CreateCityGUI.CityNameEmpty");
            newCityStatusMessage.setText(error);
            newCityStatusMessage.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return;
        }
        try {
            businessLogic.createCity(city);
            String success = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("CityCreated");
            newCityStatusMessage.setText(success);
            newCityStatusMessage.getStyleClass().setAll("label", "lbl-success");

        } catch (CityAlreadyExistException e) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("CreateCityGUI.CityAlreadyExist");
            newCityStatusMessage.setText(error);
            newCityStatusMessage.getStyleClass().setAll("label", "lbl-danger");

        }

        dissapearLabel();


    }

    private void dissapearLabel() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                newCityStatusMessage.setVisible(false);
            });
        }).start();
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
