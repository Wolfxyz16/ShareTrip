package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Alert;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class ViewAlertsController implements Controller {

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    @FXML
    private TableColumn<Alert, City> originCol;

    @FXML
    private TableColumn<Alert, City> destinyCol;

    @FXML
    private TableColumn<Alert, Date> rideDateCol;

    @FXML
    private TableColumn<Alert, Integer> numSeatsCol;

    @FXML
    private TableView<Alert> tblAlerts;

    private ObservableList<Alert> alerts;



    public ViewAlertsController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("ViewAlerts button is working");
        originCol.setCellValueFactory(new PropertyValueFactory<>("fromLocation"));
        destinyCol.setCellValueFactory(new PropertyValueFactory<>("toLocation"));
        rideDateCol.setCellValueFactory(new PropertyValueFactory<>("rideDate"));
        numSeatsCol.setCellValueFactory(new PropertyValueFactory<>("numSeats"));
        alerts = FXCollections.observableArrayList();
        alerts.addAll(businessLogic.getAlerts());
        this.tblAlerts.setItems(alerts);
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

    public TableColumn<Alert, Date> getRideDateCol() {
        return rideDateCol;
    }

    public void setRideDateCol(TableColumn<Alert, Date> rideDateCol) {
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
}