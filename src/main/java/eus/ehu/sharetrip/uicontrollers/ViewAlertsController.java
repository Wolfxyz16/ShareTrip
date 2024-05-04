package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Alert;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.ui.MainGUI;
import eus.ehu.sharetrip.utils.Dates;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class ViewAlertsController implements Controller {

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    @FXML
    private TableColumn<Alert, City> originCol;

    @FXML
    private TableColumn<Alert, City> destinyCol;

    @FXML
    private TableColumn<Alert, String> rideDateCol;

    @FXML
    private TableColumn<Alert, Integer> numSeatsCol;

    @FXML
    private TableView<Alert> tblAlerts;

    @FXML
    private Label errorlbl;

    private ObservableList<Alert> alerts;

    public ViewAlertsController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewAlerts button is working");
        originCol.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        destinyCol.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        //rideDateCol.setCellValueFactory(new PropertyValueFactory<>("rideDate"));
        numSeatsCol.setCellValueFactory(new PropertyValueFactory<>("numSeats"));

        rideDateCol.setCellValueFactory(cellData -> {
            LocalDate localdate = cellData.getValue().getRideDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            Locale locale = Locale.ENGLISH;
            String formattedDate = Dates.formatLocalizedDate(localdate,locale);
            return new SimpleStringProperty(formattedDate);
        });

        alerts = FXCollections.observableArrayList();
        alerts.addAll(businessLogic.getUserAlerts(businessLogic.getCurrentUser()));
        this.tblAlerts.setItems(alerts);
    }

    @FXML
    void deleteAlert(ActionEvent event) {
        Alert selectedAlert = tblAlerts.getSelectionModel().getSelectedItem();
        errorlbl.setVisible(true);

        if (selectedAlert == null) {
            errorlbl.setText("Please select an alert");
            errorlbl.getStyleClass().setAll("label", "lbl-danger");
            dissapearLabel();
        } else {
            businessLogic.deleteAlert(selectedAlert);
            updateTables();
        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public TableColumn<Alert, Integer> getNumSeatsCol() {
        return numSeatsCol;
    }

    public void setNumSeatsCol(TableColumn<Alert, Integer> numSeatsCol) {
        this.numSeatsCol = numSeatsCol;
    }

    public TableColumn<Alert, City> getOriginCol() {
        return originCol;
    }

    public void setOriginCol(TableColumn<Alert, City> originCol) {
        this.originCol = originCol;
    }

    public TableColumn<Alert, City> getDestinyCol() {
        return destinyCol;
    }

    public void setDestinyCol(TableColumn<Alert, City> destinyCol) {
        this.destinyCol = destinyCol;
    }

    public TableColumn<Alert, String> getRideDateCol() {
        return rideDateCol;
    }

    public void setRideDateCol(TableColumn<Alert, String> rideDateCol) {
        this.rideDateCol = rideDateCol;
    }

    public TableView<Alert> getTblAlerts() {
        return tblAlerts;
    }

    public void setTblAlerts(TableView<Alert> tblAlerts) {
        this.tblAlerts = tblAlerts;
    }

    public ObservableList<Alert> getAlerts() {
        return alerts;
    }

    public void setTlbAlerts(ObservableList<Alert> alerts) {
        this.alerts = alerts;
    }

    public void updateTables() {
        alerts.clear();
        alerts.addAll(businessLogic.getUserAlerts(businessLogic.getCurrentUser()));
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