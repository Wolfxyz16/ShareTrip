package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.exceptions.CityAlreadyExistException;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.AutoCompleteTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private AutoCompleteTextField newCity;

    @FXML
    private Label newCityStatusMessage;


    @FXML
    public void initialize() {
        newCity.setEntries(FXCollections.observableArrayList(
                "A Coruña", "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila",
                "Badajoz", "Baleares", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria",
                "Castellón", "Ciudad Real", "Córdoba", "Cuenca", "Girona", "Granada",
                "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Jaén", "La Rioja", "Las Palmas",
                "León", "Lleida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Ourense",
                "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia",
                "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid",
                "Vizcaya", "Zamora", "Zaragoza"
        ));
    }

    @FXML
    void createCityClick(ActionEvent event){
        System.out.println("city name is: " + newCity.getText());
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
