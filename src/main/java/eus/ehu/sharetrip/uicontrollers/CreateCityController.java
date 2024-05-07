package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.domain.City;
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

                "Madrid", "Barcelona", "Valencia", "Hondarribia", "Sevilla",
                "Zaragoza", "Málaga", "Murcia", "Palma de Mallorca",
                "Las Palmas de Gran Canaria", "Bilbo", "Alicante", "Córdoba",
                "Valladolid", "Vigo", "Gijón", "L'Hospitalet de Llobregat",
                "A Coruña", "Granada", "Elche",
                "Oviedo", "Badalona", "Cartagena", "Terrassa",
                "Jerez de la Frontera", "Sabadell", "Santa Cruz de Tenerife", "Móstoles",
                "Alcalá de Henares", "Pamplona", "Fuenlabrada", "Almería",
                "Leganés", "Burgos", "Santander",
                "Castellón de la Plana", "Getafe", "Albacete", "Alcorcón",
                "Logroño", "San Cristóbal de La Laguna", "Badajoz", "Salamanca",
                "Huelva", "Lleida", "Marbella", "Tarragona",
                "León", "Cádiz"


        ));
    }

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
            City created = businessLogic.createCity(city);
            System.out.println("City created: " + created.getName());
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
                Thread.sleep(3000);
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
