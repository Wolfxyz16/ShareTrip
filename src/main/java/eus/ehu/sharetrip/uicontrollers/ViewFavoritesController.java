package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.Ride;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
public class ViewFavoritesController implements Controller {


    @FXML
    private TableColumn<Ride, City> arrCityCol;

    @FXML
    private TableColumn<Ride, Date> dateCol;

    @FXML
    private TableColumn<Ride, City> depCityCol;

    @FXML
    private TableColumn<Ride, Integer> seatsCol;

    @FXML
    private TableView<Ride> tblFavorite;

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
        dateCol.setCellValueFactory(new PropertyValueFactory<>("rideDate"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("numSeats"));

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
}