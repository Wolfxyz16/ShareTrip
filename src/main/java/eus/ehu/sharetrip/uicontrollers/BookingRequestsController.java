package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.configuration.UtilDate;
import eus.ehu.sharetrip.domain.*;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Date;

public class BookingRequestsController implements Controller  {

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    @FXML
    private TableColumn<Reservation, String> requestAplicantName;

    @FXML
    private TableColumn<Reservation, String> requestRideFrom;

    @FXML
    private TableColumn<Reservation, String> requestRideTo;

    @FXML
    private TableColumn<Reservation, Integer> requestedSeats;

    @FXML
    private TableColumn<Reservation, Date> rideDate;

    @FXML
    private TableView<Reservation> tblMyRidesBooks;


    public BookingRequestsController(BlFacade bl) {
        this.businessLogic = bl;
    }

    @FXML
    void initialize() {
        System.out.println("BookingRequests button is working");
        requestAplicantName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, javafx.beans.value.ObservableValue<String>>() {
            @Override
            public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> param) {
                Reservation reservation = param.getValue();
                if (reservation != null && reservation.getMadeBy() != null) {
                    String username = reservation.getMadeBy().getUsername();
                    return new javafx.beans.value.ObservableValueBase<String>() {
                        @Override
                        public String getValue() {
                            return username;
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

        requestRideFrom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, javafx.beans.value.ObservableValue<String>>() {
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

        requestRideTo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, javafx.beans.value.ObservableValue<String>>() {
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
        rideDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, Date>, javafx.beans.value.ObservableValue<Date>>() {
            @Override
            public javafx.beans.value.ObservableValue<Date> call(TableColumn.CellDataFeatures<Reservation, Date> param) {
                Reservation reservation = param.getValue();
                if (reservation != null && reservation.getForRide() != null) {
                    Date date = reservation.getForRide().getDate();
                    return new javafx.beans.value.ObservableValueBase<Date>() {
                        @Override
                        public Date getValue() {
                            return date;
                        }
                    };
                } else {
                    return new javafx.beans.value.ObservableValueBase<Date>() {
                        @Override
                        public Date getValue() {
                            return UtilDate.newDate(0,0,0);
                        }
                    };
                }
            }
        });
    }

    public void updateTable() {
        tblMyRidesBooks.getItems().clear();
        ArrayList<Ride> rides = businessLogic.getRidesByDriver((Driver) businessLogic.getCurrentUser());
        for (Ride r: rides) {
            tblMyRidesBooks.getItems().addAll(r.getReservations());
        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }




}
