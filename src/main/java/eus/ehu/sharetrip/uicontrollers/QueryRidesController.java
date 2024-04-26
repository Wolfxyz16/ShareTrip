package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Driver;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.exceptions.AlertAlreadyExistException;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class QueryRidesController implements Controller {

    @FXML
    private Button bellBtn;

    @FXML
    private ImageView bellView;

    @FXML
    public ImageView heartView;

    @FXML
    public Button heartBtn;

    @FXML
    public Button searchBtn;

    @FXML
    private Label outputLabel;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<Ride, String> qc1;

    @FXML
    private TableColumn<Ride, Integer> qc2;

    @FXML
    private TableColumn<Ride, Float> qc3;

    @FXML
    private ComboBox<City> comboArrivalCity;

    @FXML
    private ComboBox<City> comboDepartCity;

    @FXML
    private TextField numSeats;

    @FXML
    private TableView<Ride> tblRides;

    private List<LocalDate> datesWithBooking = new ArrayList<>();

    private final BlFacade businessLogic;

    public QueryRidesController(BlFacade bl) {
        businessLogic = bl;
    }

    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year, month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            datesWithBooking.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    // we need to mark (highlight in pink) the events for the previous, current and next month
    // this method will be called when the user clicks on the << or >> buttons
    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }


    private void updateDatePickerCellFactory(DatePicker datePicker) {

        List<Date> dates = businessLogic.getDatesWithRides(comboDepartCity.getValue(), comboArrivalCity.getValue());

        // extract datesWithBooking from rides
        datesWithBooking.clear();
        for (Date day : dates) {
            datesWithBooking.add(Dates.convertToLocalDateViaInstant(day));
        }

        // update the DatePicker cells
        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (item.equals(LocalDate.now())) {
                                this.setStyle("-fx-background-color: #ADD8E6");
                            } else if (datesWithBooking.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            } else {
                                this.setStyle("-fx-background-color: rgb(235, 235, 235)");
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    void initialize() {

        // Update DatePicker cells when ComboBox value changes
        comboArrivalCity.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateDatePickerCellFactory(datepicker));

        ObservableList<City> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());

        ObservableList<City> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        // when the user selects a departure city, update the arrival cities
        comboDepartCity.setOnAction(e -> {
                arrivalCities.clear();
            try {
                if ((comboDepartCity.getValue() != null)) {
                    arrivalCities.setAll(businessLogic.getDestinationCities(businessLogic.getCity(comboDepartCity.getValue())));

                }
            } catch (CityDoesNotExistException ex) {
                  //it's not supposed to happen ever
            }
        });

        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {


                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();

                    String month = ((Label) (labels.get(0))).getText();
                    String year = ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);

                    // print month value
                    System.out.println("Month:" + ym.getMonthValue());

                });
            });
        });

        // Query Rides logic
        searchBtn.setOnAction(actionEvent -> {

            // Clear the output label
            outputLabel.setText("");
            outputLabel.getStyleClass().setAll("label");

            // Check if all fields are not empty and logically correct
            if (noErrorsInInputFields()) {
                // Clear the table
                tblRides.getItems().clear();
                try {
                    List<Ride> rides = businessLogic.getRides(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()), Integer.parseInt(numSeats.getText()));

                    // If for this search there is an alert, show the red alert image
                    try {
                        if (businessLogic.alertAlreadyExist(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()),  Integer.parseInt(numSeats.getText()))) {
                            Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redAlert.png"));
                            bellView.setImage(image);
                        } else {
                            Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/alert.png"));
                            bellView.setImage(image);
                        }
                    } catch (CityDoesNotExistException e) {
                        //it's not supposed to happen ever
                    }

                    // TODO: if the search has an alert for it do not reset the heart image
                    /*if (rideIsFav()) {
                        Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redHeart.png"));
                        heartView.setImage(image);
                    } else { */
                    Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Heart.png"));
                    heartView.setImage(image);
                    //}

                    // If the search result is empty, show a message and return
                    if (rides.isEmpty()) {
                        outputLabel.setText("No rides available for you with the selected date, cities and number of seats.");
                        outputLabel.getStyleClass().setAll("label", "lbl-warning");
                        return;
                    }

                    // If the search is successful, show a success message and not empty
                    outputLabel.setText("These are the available rides for you:");
                    outputLabel.getStyleClass().setAll("label", "lbl-success");

                    for (Ride ride : rides) {
                        tblRides.getItems().add(ride);
                    }
                    } catch (CityDoesNotExistException ex) {
                        //it's not supposed to happen ever
                    }
            }
        });

        // show just the driver's name in column1
        qc1.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ride, String> data) {
                Driver driver = data.getValue().getDriver();
                return new SimpleStringProperty(driver != null ? driver.getUsername() : "<no name>");
            }
        });

        qc2.setCellValueFactory(new PropertyValueFactory<>("numPlaces"));
        qc3.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private boolean noErrorsInInputFields() {
        // Check if all fields are filled
        if (comboDepartCity.getValue() == null) {
            outputLabel.setText("Please select a departure city.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }
        if (comboArrivalCity.getValue() == null) {
            outputLabel.setText("Please select an arrival city.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }
        if (datepicker.getValue() == null) {
            outputLabel.setText("Please select a date.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }
        if (numSeats.getText() == null || numSeats.getText().isEmpty()) {
            outputLabel.setText("Please enter the number of seats.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }

        // Check if all fields are logically correct

        // Check if date is later than today
        if (datepicker.getValue().isBefore(LocalDate.now())) {
            outputLabel.setText("The date must be later than today.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }

        // A date is selected and not only a number input or something added to the datepicker
        if (datepicker.getValue() == null) {
            outputLabel.setText("Please select a date.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }

        // Check if the number of seats is a positive integer
        try {
            int seats = Integer.parseInt(numSeats.getText());
            if (seats <= 0) {
                outputLabel.setText("The number of seats must be a positive integer.");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                return false;
            }
        } catch (NumberFormatException e) {
            outputLabel.setText("The number of seats must be a positive integer.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            return false;
        }

        // If all checks pass, return true
        return true;
    }


/*

  private void setupEventSelection() {
    tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {

        tblQuestions.getItems().clear();
        for (Question q : tblEvents.getSelectionModel().getSelectedItem().getQuestions()) {
          tblQuestions.getItems().add(q);
        }
      }
    });
  }
*/

    public void resetValues() {
        comboDepartCity.setValue(null);
        comboArrivalCity.setValue(null);
        datepicker.setValue(null);
        numSeats.setText("");
        tblRides.getItems().clear();
        outputLabel.setText("");
        outputLabel.getStyleClass().setAll("label");
        Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Heart.png"));
        heartView.setImage(image);
        Image image2 = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Alert.png"));
        bellView.setImage(image2);
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
    }

    @FXML
    public void addToFavorite(ActionEvent actionEvent) {
        // TODO: Implement add to Favorites logic,
        // For that take a look at the createNewAlert method and how it is implemented
        /*Ride ride = tblRides.getSelectionModel().getSelectedItem();
        if (ride != null) {
            businessLogic.addToFavorite(ride);
        }
         */

        Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redHeart.png"));
        heartView.setImage(image);

    }

    @FXML
    public void createNewAlert(ActionEvent actionEvent) throws AlertAlreadyExistException, CityDoesNotExistException {
        if (noErrorsInInputFields()) {
            // check if user is logged in
            if (businessLogic.getCurrentUser() == null) {
                outputLabel.setText("You must be logged in to create an alert.");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                return;
            } else if (businessLogic.alertAlreadyExist(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()),  Integer.parseInt(numSeats.getText()))) {
                outputLabel.setText("You already have this alert for this ride.");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                return;
            }
            try {
              businessLogic.createAlert(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()),  Integer.parseInt(numSeats.getText()));
            } catch (CityDoesNotExistException ex) {
                  //it's not supposed to happen ever
            }
            Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redAlert.png"));
            bellView.setImage(image);
            System.out.println("Alert created");

        }
    }
}
