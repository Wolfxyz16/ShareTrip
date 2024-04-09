package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.User;
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
    private TextField txtPrice;

    private String field_Errors() {

        try {
            if ((txtDepartCity.getText().length() == 0) || (txtArrivalCity.getText().length() == 0) || (txtSeats.getText().length() == 0) || (txtPrice.getText().length() == 0))
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

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant());
        String departCity = txtDepartCity.getText();
        String arrivalCity = txtArrivalCity.getText();
        int numSeats = Integer.parseInt( txtSeats.getText() );
        float price = Float.parseFloat( txtPrice.getText() );
        User user = bl.getCurrentUser();

        try {
            bl.createRide(departCity, arrivalCity, date, numSeats, price, user.getId());
        } catch (RideAlreadyExistException ex) {
            // set the corresponding error labels
            throw new RuntimeException(ex);
        } catch (RideMustBeLaterThanTodayException ex) {
            // set the corresponding error labels
            throw new RuntimeException(ex);
        }

        //  Event event = comboEvents.getSelectionModel().getSelectedItem();
        String errors = field_Errors();

        if (errors != null) {
            // businessLogic.createQuestion(event, inputQuestion, inputPrice);

        } else {
            try {
                Ride r = bl.createRide(txtDepartCity.getText(), txtArrivalCity.getText(), Dates.convertToDate(datePicker.getValue()), numSeats, price, user.getId());

            } catch (RideMustBeLaterThanTodayException e1) {

            } catch (RideAlreadyExistException e1) {

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
