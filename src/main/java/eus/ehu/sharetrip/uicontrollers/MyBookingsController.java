package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Reservation;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Date;

public class MyBookingsController implements Controller {

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    @FXML
    private TableColumn<Reservation, Date> requestDate;

    @FXML
    private TableColumn<Reservation, String> requestFrom;

    @FXML
    private TableColumn<Reservation, String> requestTo;

    @FXML
    private TableColumn<Reservation, Integer> requestedSeats;

    @FXML
    private TableView<Reservation> tblMyBookings;

    public MyBookingsController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("MyBookkings button is working");
        requestDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("requestDate"));
        requestFrom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, ObservableValue<String>>() {
            @Override
            public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> param) {
                Reservation reservation = param.getValue();
                if (reservation != null && reservation.getForRide() != null) {
                    String from = reservation.getForRide().getFromLocation().getName();
                    return new javafx.beans.value.ObservableValueBase<String>() {
                        @Override
                        public String getValue() {
                            return from;
                        }
                    };
                } else {
                    return new javafx.beans.value.ObservableValueBase<String>() {
                        @Override
                        public String getValue() {
                            return "";
                        }
                    };
                }
            }
        });

        requestTo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, javafx.beans.value.ObservableValue<String>>() {
            @Override
            public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> param) {
                Reservation reservation = param.getValue();
                if (reservation != null && reservation.getForRide() != null) {
                    String to = reservation.getForRide().getToLocation().getName();
                    return new javafx.beans.value.ObservableValueBase<String>() {
                        @Override
                        public String getValue() {
                            return to;
                        }
                    };
                } else {
                    return new javafx.beans.value.ObservableValueBase<String>() {
                        @Override
                        public String getValue() {
                            return "";
                        }
                    };
                }
            }
        });
        requestedSeats.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("numSeats"));

    }

    public void updateTable() {
        tblMyBookings.getItems().clear();
        tblMyBookings.getItems().addAll(businessLogic.getMyBookings(businessLogic.getCurrentUser()));
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
