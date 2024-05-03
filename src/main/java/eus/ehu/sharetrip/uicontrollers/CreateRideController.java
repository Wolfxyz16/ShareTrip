package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistException;
import eus.ehu.sharetrip.exceptions.RideAlreadyExistException;
import eus.ehu.sharetrip.exceptions.RideMustBeLaterThanTodayException;
import eus.ehu.sharetrip.utils.SafeLocalDateStringConverter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.Dates;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateRideController implements Controller {

    public CreateRideController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }

    private final BlFacade businessLogic;

    private MainGUI mainGUI;

    @FXML
    private Button btnCreateRide;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<City> comboArrivalCity;

    @FXML
    private ComboBox<City> comboDepartCity;

    @FXML
    private TextField txtSeats;

    @FXML
    private Label warningsInfo;

    @FXML
    private TextField txtPrice;

    private String field_Errors() {

        try {
            if (comboDepartCity.getValue() == null || (comboArrivalCity.getValue() == null) || (txtSeats.getText().isEmpty()) || (txtPrice.getText().isEmpty())) {
                return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
            } else {

                // trigger an exception if the introduced string is not a number
                int inputSeats = Integer.parseInt(txtSeats.getText());

                if (inputSeats <= 0) {
                    return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SeatsMustBeGreaterThan0");
                } else {
                    float price = Float.parseFloat(txtPrice.getText());
                    if (price <= 0)
                        return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");

                    else
                        return null;

                }
            }
        } catch (NumberFormatException e1) {

            return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");
        } catch (Exception e1) {

            e1.printStackTrace();
            return null;

        }
    }

    /*
        This is the method that is called when the user clicks on the CreateRide button
     */
    @FXML
    void createRideClick(ActionEvent e) {
        //  Event event = comboEvents.getSelectionModel().getSelectedItem();
        warningsInfo.setText("");
        warningsInfo.getStyleClass().setAll("label");
        String errors = field_Errors();
        warningsInfo.setVisible(true);

        if (errors != null) {
            warningsInfo.setText(errors);
            warningsInfo.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();

        } else {
            LocalDate localDate = datePicker.getValue();
            Date date = Date.from(localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant());
            City departCity = comboDepartCity.getValue();
            City arrivalCity = comboArrivalCity.getValue();
            int numSeats = Integer.parseInt( txtSeats.getText() );
            float price = Float.parseFloat( txtPrice.getText() );
            User user = businessLogic.getCurrentUser();

            try {
                businessLogic.createRide(departCity, arrivalCity, Dates.convertToDate(datePicker.getValue()), numSeats, price, user.getId());
                warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));
                warningsInfo.getStyleClass().setAll("label", "lbl-success");
                dissapearLabel();

            } catch (RideMustBeLaterThanTodayException e1) {
                warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
                warningsInfo.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();

            } catch (RideAlreadyExistException e1) {
                warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideAlreadyExist"));
                warningsInfo.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
            }
        }
    }

    @FXML
    void initialize() {
        // Set converter to catch invalid dates
        datePicker.setConverter(new SafeLocalDateStringConverter(warningsInfo));

        ObservableList<City> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        ObservableList<City> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

        comboDepartCity.getItems().addAll(businessLogic.getAllCities());

        // Set the arrival cities based on the selected departure city, so remove the departure city
        comboDepartCity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                departureCities.setAll(businessLogic.getAllCities());
                departureCities.remove(newValue);
                comboArrivalCity.setItems(departureCities);
            }
        });
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void clearFields() {
        comboDepartCity.getSelectionModel().clearSelection();
        comboArrivalCity.getSelectionModel().clearSelection();
        txtSeats.clear();
        txtPrice.clear();
        datePicker.getEditor().clear();

        // Add new cities to the combo box
        comboDepartCity.getItems().clear();
        comboDepartCity.getItems().addAll(businessLogic.getAllCities());
        comboArrivalCity.getItems().clear();
    }

    private void dissapearLabel() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                warningsInfo.setVisible(false);
            });
        }).start();
    }
}
