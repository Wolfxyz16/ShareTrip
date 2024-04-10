package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Driver;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.exceptions.CityDoesNotExistExeception;
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

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class QueryRidesController implements Controller {

    @FXML
    public ImageView heartView;

    @FXML
    public Button loveBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<Ride, String> qc1;

    @FXML
    private TableColumn<Ride, Integer> qc2;

    @FXML
    private TableColumn<Ride, Float> qc3;

    @FXML
    private ComboBox<String> comboArrivalCity;

    @FXML
    private ComboBox<String> comboDepartCity;

//  @FXML
//  private TableView<Event> tblEvents;

    @FXML
    private TableView<Ride> tblRides;


    private MainGUI mainGUI;

    private List<LocalDate> datesWithBooking = new ArrayList<>();

    private BlFacade businessLogic;

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

        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (datesWithBooking.contains(item)) {
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

        ObservableList<String> departureCities = FXCollections.observableArrayList(new ArrayList<>());
        departureCities.setAll(businessLogic.getDepartCities());

        ObservableList<String> arrivalCities = FXCollections.observableArrayList(new ArrayList<>());

        comboDepartCity.setItems(departureCities);
        comboArrivalCity.setItems(arrivalCities);

        // when the user selects a departure city, update the arrival cities
        try{
            City depCity = businessLogic.getCity(comboDepartCity.getValue());
            comboDepartCity.setOnAction(e -> {
                arrivalCities.clear();
                arrivalCities.setAll(businessLogic.getDestinationCities(depCity));
            });
        } catch (CityDoesNotExistExeception e) {
            throw new RuntimeException(e);
        };

        // a date has been chosen, update the combobox of Rides
        datepicker.setOnAction(actionEvent -> {
            if(comboDepartCity.getValue() != null && comboArrivalCity.getValue() != null) {

                tblRides.getItems().clear();
                try {
                    City depCity = businessLogic.getCity(comboDepartCity.getValue());
                    City arrCity = businessLogic.getCity(comboArrivalCity.getValue());
                    List<Ride> rides = businessLogic.getRides(depCity, arrCity, Dates.convertToDate(datepicker.getValue()));
                // List<Ride> rides = Arrays.asList(new Ride("Bilbao", "Donostia", Dates.convertToDate(datepicker.getValue()), 3, 3.5f, new Driver("pepe@pepe.com", "pepe")));
                    for (Ride ride : rides) {
                        tblRides.getItems().add(ride);
                    }
                } catch (CityDoesNotExistExeception e) {
                    throw new RuntimeException(e);
                }
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

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    public void addToFavorite(ActionEvent actionEvent) {
        /**Ride ride = tblRides.getSelectionModel().getSelectedItem();
        if (ride != null) {
            businessLogic.addToFavorite(ride);
        }
         */

        Image image = new Image(getClass().getResourceAsStream("/eus/ehu/sharetrip/ui/assets/redHeart.png"));
        heartView.setImage(image);

    }
}
