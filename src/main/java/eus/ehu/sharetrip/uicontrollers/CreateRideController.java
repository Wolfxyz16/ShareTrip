package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistException;
import eus.ehu.sharetrip.exceptions.RideAlreadyExistException;
import eus.ehu.sharetrip.exceptions.RideMustBeLaterThanTodayException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.Dates;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateRideController implements Controller {

    public CreateRideController(BlFacade bl) {
        this.bl = bl;
    }

    private BlFacade bl;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    @FXML
    private Button btnCreateRide;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtDepartCity;

    @FXML
    private TextField txtArrivalCity;

    @FXML
    private TextField txtSeats;

    @FXML
    private Label warningsInfo;

    @FXML
    private TextField txtPrice;

    private String field_Errors() {

        try {
            if ((txtDepartCity.getText().isEmpty()) || (txtArrivalCity.getText().isEmpty()) || (txtSeats.getText().isEmpty()) || (txtPrice.getText().isEmpty()))
                return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
            else {

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

        if (errors != null) {
            warningsInfo.setText(errors);
            warningsInfo.getStyleClass().setAll("label", "lbl-danger");

        } else {
            LocalDate localDate = datePicker.getValue();
            Date date = Date.from(localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant());
            City departCity = new City (txtDepartCity.getText());
            City arrivalCity = new City(txtArrivalCity.getText());
            int numSeats = Integer.parseInt( txtSeats.getText() );
            float price = Float.parseFloat( txtPrice.getText() );
            User user = bl.getCurrentUser();

            try{
                City depart = bl.getCity(departCity);
                try {
                    City arrival = bl.getCity(arrivalCity);
                    bl.createRide(depart, arrival, Dates.convertToDate(datePicker.getValue()), numSeats, price, user.getId());
                    warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));
                    warningsInfo.getStyleClass().setAll("label", "lbl-success");
                } catch (RideMustBeLaterThanTodayException e1) {
                    warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
                    warningsInfo.getStyleClass().setAll("label", "lbl-danger");
                } catch (RideAlreadyExistException e1) {
                    warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideAlreadyExist"));
                    warningsInfo.getStyleClass().setAll("label", "lbl-danger");
                }catch (CityDoesNotExistException e1) {
                    warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.DestinationCityDoesNotExist"));
                    warningsInfo.getStyleClass().setAll("label", "lbl-danger");
                }
            }catch (CityDoesNotExistException e1) {
                warningsInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.DepartureCityDoesNotExist"));
                warningsInfo.getStyleClass().setAll("label", "lbl-danger");
            }

        }



    }

    @FXML
    void initialize() {}

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
