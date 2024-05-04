package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.*;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.Dates;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.scene.control.cell.PropertyValueFactory;
public class ViewFavoritesController implements Controller {

    @FXML
    private TableColumn<Ride, City> arrCityCol;

    @FXML
    private TableColumn<Ride, City> depCityCol;


    @FXML
    private TableView<Ride> tblFavorite;

    @FXML
    private Label errorlbl;

    @FXML
    private Button searchbtn;

    private ObservableList<Ride> favoriteRides;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public ViewFavoritesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {

        System.out.println("ViewFavorites button is working");
        depCityCol.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        arrCityCol.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        //driverCol.setCellValueFactory(new PropertyValueFactory<>("driver"));
        //dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));


        /*
        // Set the weekday for the date column
        dateCol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Dates.getWeekdayFromDate(cellData.getValue().getDate()).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        });*/

        favoriteRides = FXCollections.observableArrayList();
        tblFavorite.setItems(favoriteRides);
    }

    public void updateTables() {
        favoriteRides.clear();
        User currentUser = businessLogic.getCurrentUser();
        List<Ride> favRides = businessLogic.getFavoriteRides(currentUser);
        this.favoriteRides.addAll(favRides);

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    void searchFav(ActionEvent event) {
        // DayOfWeek weekday = Dates.getWeekdayFromDate(selectedRide.getDate());
        errorlbl.setVisible(true);
        try {
            Ride selectedRide = tblFavorite.getSelectionModel().getSelectedItem();
            City depCity = selectedRide.getFromLocation();
            City arrCity = selectedRide.getToLocation();
            mainGUI.searchFavRide(depCity, arrCity);
        } catch (Exception e) {
            errorlbl.setText("Please select a ride");
            errorlbl.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
        }
    }

    @FXML
    void deleteFav(ActionEvent event) {
        Ride selectedRide = tblFavorite.getSelectionModel().getSelectedItem();
        errorlbl.setVisible(true);

        if (selectedRide == null) {
            errorlbl.setText("Please select a ride");
            errorlbl.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
        } else {
            User currentUser = businessLogic.getCurrentUser();
            businessLogic.deleteFavoriteRide(currentUser, selectedRide);
            updateTables();
        }
    }

    private void dissapearLabel() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                errorlbl.setVisible(false);
            });
        }).start();
    }
}