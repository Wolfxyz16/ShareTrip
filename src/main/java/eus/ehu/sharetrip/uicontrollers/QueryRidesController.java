package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Driver;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.Traveler;
import eus.ehu.sharetrip.exceptions.AlertAlreadyExistException;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistException;
import eus.ehu.sharetrip.utils.SafeLocalDateStringConverter;
import javafx.application.Platform;
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
    private Button bookBtn;

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
    private ComboBox<Integer> numSeats;

    @FXML
    private TableView<Ride> tblRides;

    private final List<LocalDate> datesWithBooking = new ArrayList<>();

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
        updateButtonVisibilityDependingOnUserType();
        // Set converter to catch invalid dates
        datepicker.setConverter(new SafeLocalDateStringConverter(outputLabel));

        // Update DatePicker cells when ComboBox value changes
        comboArrivalCity.valueProperty().addListener(
                (obs, oldVal, newVal) -> updateDatePickerCellFactory(datepicker));

        ObservableList<City> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());

        ObservableList<City> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());
        numSeats.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        // when the user selects a departure city, update the arrival cities

        comboDepartCity.setOnAction(e -> {
                if ((comboDepartCity.getValue() != null)) {
                    Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/alert.png"));
                    bellView.setImage(image);
                }
        });

        arrivalCities.setAll(businessLogic.getAllCities());

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

        tblRides.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && businessLogic.getCurrentUser() != null) {
                Ride ride = tblRides.getSelectionModel().getSelectedItem();
                updateFavsButton(ride);
            }
        });
    }

    private void updateAlertsButton() throws CityDoesNotExistException {
        Image image;

        // if the search has an alert set the bell icon to red, if not set it to normal
        if (businessLogic.alertAlreadyExist(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()), numSeats.getValue(), businessLogic.getCurrentUser())) {
            image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redAlert.png"));
            bellView.setImage(image);
        } else {
            image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/alert.png"));
            bellView.setImage(image);
        }
    }


    private void updateFavsButton(Ride ride) {
        if (ride != null && businessLogic.getCurrentUser() != null) {
            if (businessLogic.getCurrentUser().getFavRides().contains(ride)) {
                Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redHeart.png"));
                heartView.setImage(image);
            } else {
                Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Heart.png"));
                heartView.setImage(image);
            }
        }
    }

    private boolean noErrorsInInputFields() {
        outputLabel.setVisible(true);
        // Check if all fields are filled
        if (comboDepartCity.getValue() == null) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("EmptyDepartureCity");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }
        if (comboArrivalCity.getValue() == null) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("EmptyArrivalCity");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }
        if (datepicker.getValue() == null) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("EmptyDate");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }
        if (numSeats.getValue() == null) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("EmptyNumSeats");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }

        // Check if all fields are logically correct

        // Check if date is later than today
        if (datepicker.getValue().isBefore(LocalDate.now())) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("CreateRideGUI.ErrorRideMustBeLaterThanToday");
            outputLabel.setText("The date must be later than today.");
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }

        // A date is selected and not only a number input or something added to the datepicker
        if (datepicker.getValue() == null) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("EmptyDate");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }

        // Check if the number of seats is a positive integer
        try {
            int seats = numSeats.getValue();
            if (seats <= 0) {//not possible
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("NumSeatsNotPositiveInteger");
            outputLabel.setText(error);
            outputLabel.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
            return false;
        }

        // If all checks pass, return true
        return true;
    }

    public void resetValues() {
        comboDepartCity.setValue(null);
        comboArrivalCity.setValue(null);
        datepicker.setValue(null);
        numSeats.setValue(null);
        tblRides.getItems().clear();
        //outputLabel.setText("");
        //outputLabel.getStyleClass().setAll("label");
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
        if (noErrorsInInputFields()) {
            // check if user is logged in
            if (businessLogic.getCurrentUser() == null) {
                String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("ErrorMustBeLoggedIn");
                outputLabel.setText(error);
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
                return;
            } else if (businessLogic.favoriteAlreadyExist(businessLogic.getCurrentUser(), tblRides.getSelectionModel().getSelectedItem())) {
                outputLabel.setText("The favorite already exists");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
                return;
            } else if (tblRides.getSelectionModel().getSelectedItem() == null) {
                outputLabel.setText("Please select a ride to add to favorites.");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
                return;
            }

            Ride selectedRide = tblRides.getSelectionModel().getSelectedItem();
            businessLogic.addFavoriteRide(businessLogic.getCurrentUser(), selectedRide);
            outputLabel.setText("Ride added to favorites.");
            outputLabel.getStyleClass().setAll("label", "lbl-success");
            dissapearLabel();

            updateFavsButton(selectedRide);
            System.out.println("Favorite created");
        }
    }

    @FXML
    public void createNewAlert(ActionEvent actionEvent) throws AlertAlreadyExistException, CityDoesNotExistException {
        if (noErrorsInInputFields()) {
            // check if user is logged in
            if (businessLogic.getCurrentUser() == null) {
                String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("ErrorMustBeLoggedIn");
                outputLabel.setText(error);
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
                return;
            } else if (businessLogic.alertAlreadyExist(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()), numSeats.getValue(), businessLogic.getCurrentUser())) {
                String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("CreateAlertGUI.AlertAlreadyExist");
                outputLabel.setText(error);
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
                return;
            }else
            try {
                businessLogic.createAlert(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()), numSeats.getValue(), businessLogic.getCurrentUser());
            } catch (CityDoesNotExistException ex) {
                //it's not supposed to happen ever


            }
            updateAlertsButton();
            System.out.println("Alert created");
        }
    }

    @FXML
    void bookRideAction(ActionEvent event) {
        if (noErrorsInInputFields()) {
            if (businessLogic.getCurrentUser() == null) {
                String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("ErrorMustBeLoggedIn");
                outputLabel.setText(error);
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
            } else if (tblRides.getSelectionModel().getSelectedItem() == null) {
                outputLabel.setText("Please select a ride to book.");
                outputLabel.getStyleClass().setAll("label", "lbl-danger");
                dissapearLabel();
            } else {
                Ride selectedRide = tblRides.getSelectionModel().getSelectedItem();
                businessLogic.bookRide((Traveler) businessLogic.getCurrentUser(), selectedRide, numSeats.getValue());
                outputLabel.setText("Ride booked successfully.");
                outputLabel.getStyleClass().setAll("label", "lbl-success");
                dissapearLabel();
                resetValues();
            }

        }
    }

    public void searchFavRide(City depCity, City arrCity, Date date) {
        comboDepartCity.setValue(depCity);
        comboArrivalCity.setValue(arrCity);
        numSeats.setValue(1);
        datepicker.setValue(Dates.convertToLocalDateViaInstant(date));
    }

    public void searchRides(ActionEvent actionEvent) {
        outputLabel.setText("");
        outputLabel.getStyleClass().setAll("label");
        heartView.setImage(new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Heart.png")));

        // Check if all fields are not empty and logically correct
        if (noErrorsInInputFields()) {
            // Clear the table
            tblRides.getItems().clear();

            try {
                //get all rides with the selected parameters
                List<Ride> rides = businessLogic.getRides(businessLogic.getCity(comboDepartCity.getValue()), businessLogic.getCity(comboArrivalCity.getValue()), Dates.convertToDate(datepicker.getValue()), numSeats.getValue());

                updateAlertsButton();

                // If the search result is empty, show a message and return
                if (rides.isEmpty()) {
                    String error = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("NoRidesAvailable");
                    outputLabel.setText(error);
                    outputLabel.getStyleClass().setAll("label", "lbl-warning");
                    dissapearLabel();
                    return;
                }

                // List all rides result
                for (Ride ride : rides) {
                    tblRides.getItems().add(ride);
                }

                // If the search is successful, show a success message and not empty
                String success = ResourceBundle.getBundle("Etiquetas", Locale.getDefault()).getString("RidesAvailable");
                outputLabel.setText(success);
                dissapearLabel();

                Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/Heart.png"));
                heartView.setImage(image);

                outputLabel.setText("These are the available rides for you:");
                outputLabel.getStyleClass().setAll("label", "lbl-success");
                dissapearLabel();

                tblRides.getSelectionModel().selectedItemProperty().addListener((obs, oldRide, newRide) -> {
                    if (newRide != null && businessLogic.getCurrentUser() != null) {
                        updateFavsButton(newRide);
                    }
                });

            } catch (CityDoesNotExistException ex) {
                //it's not supposed to happen ever
            }
        }
    }


    private void dissapearLabel() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                outputLabel.setVisible(false);
            });
        }).start();
    }


    private void updateButtonVisibilityDependingOnUserType() {
        if (businessLogic.getCurrentUser() instanceof Driver) {
            bookBtn.setVisible(false);
            heartBtn.setVisible(false);
            bellBtn.setVisible(false);
        } else {
            bookBtn.setVisible(true);
            heartBtn.setVisible(true);
            bellBtn.setVisible(true);
        }
    }

    public void clearFields() {
        comboDepartCity.getItems().clear();
        comboDepartCity.getItems().addAll(businessLogic.getAllCities());
        comboArrivalCity.getItems().clear();
        comboArrivalCity.getItems().addAll(businessLogic.getAllCities());
    }
}