package eus.ehu.sharetrip.ui;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.City;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.uicontrollers.MainGUIController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import eus.ehu.sharetrip.uicontrollers.Controller;
import eus.ehu.sharetrip.uicontrollers.*;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainGUI {

    private BlFacade businessLogic;

    private BorderPane mainWrapper;

    private Stage stage;

    public BlFacade getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic(BlFacade afi) {
        businessLogic = afi;
    }


    public MainGUI(BlFacade bl) {
        Platform.startup(() -> {
            try {
                setBusinessLogic(bl);
                init(stage=new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Window mainWin, createRideWin, queryRidesWin, loginWin, registerWin, favoriteOverviewWin, alertOverviewWin,
            createCityWin, messagesOverviewWin, sendMessageWin, viewMessagesWin, myBookings, bookingRequests, logOutWin, videoWin;

    public void sendAlertEmail(User sentTo, City departCity, City arrivalCity, Date date, int numSeats) {
        ((MyMessagesController)viewMessagesWin.c).sendAlertEmail(sentTo, departCity, arrivalCity, date, numSeats);
    }

    class Window {
        Controller c;
        Parent ui;
    }

    private Window load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader( MainGUI.class.getResource(fxml), ResourceBundle.getBundle("Etiquetas", Locale.getDefault() ));
            loader.setControllerFactory( controllerClass -> {
                try {
                    return controllerClass
                            .getConstructor(BlFacade.class)
                            .newInstance(businessLogic);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent ui = loader.load();
            Controller controller = loader.getController();
            controller.setMainApp(this);

            Window window = new Window();
            window.c = controller;
            window.ui = ui;

            return window;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showScene(String scene) {

        switch (scene) {
            case "Home" -> mainWrapper.setCenter(mainWin.ui);
            case "View Alert" -> {
                mainWrapper.setCenter(alertOverviewWin.ui);
                ((ViewAlertsController) alertOverviewWin.c).updateTables();
            }
            case "Message Overview" -> {
                mainWrapper.setCenter(messagesOverviewWin.ui);
                ((MyMessagesController) viewMessagesWin.c).updateTables();
            }
            case "Send message" -> mainWrapper.setCenter(sendMessageWin.ui);
            case "View messages" -> mainWrapper.setCenter(viewMessagesWin.ui);
            case "View Favorites" -> {
                mainWrapper.setCenter(favoriteOverviewWin.ui);
                ((ViewFavoritesController)favoriteOverviewWin.c).updateTables();
            }
            case "Query Rides" -> {
                mainWrapper.setCenter(queryRidesWin.ui);
                ((QueryRidesController) queryRidesWin.c).updateButtonVisibilityDependingOnUserType();
                ((QueryRidesController) queryRidesWin.c).resetValues();
                ((QueryRidesController) queryRidesWin.c).clearFields();
        }
            case "Create Ride" -> {
                mainWrapper.setCenter(createRideWin.ui);
                ((CreateRideController) createRideWin.c).clearFields();
            }
            case "Log in" -> {
                mainWrapper.setCenter(loginWin.ui);
                ((SignInController)loginWin.c).clearFields();
            }
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
            case "Create City" -> mainWrapper.setCenter(createCityWin.ui);
            case "MyBookings" -> {
                mainWrapper.setCenter(myBookings.ui);
                ((MyBookingsController)myBookings.c).updateTable();
            }
            case "BookingRequests" -> {
                mainWrapper.setCenter(bookingRequests.ui);
                ((BookingRequestsController)bookingRequests.c).updateTable();
            }
            case "Log Out" -> mainWrapper.setCenter(logOutWin.ui);
            case "Video" -> {
                mainWrapper.setCenter(videoWin.ui);
                ((videoController)videoWin.c).visibleVideo();}
        }
    }

    public void init(Stage stage) throws IOException {
        mainWin = load("MainGUI.fxml");
        mainWrapper = ((MainGUIController)mainWin.c).getMainWrapper();

        createRideWin = load("CreateRide.fxml");
        queryRidesWin = load("QueryRides.fxml");
        loginWin = load("SignIn.fxml");
        registerWin = load("SignUp.fxml");
        favoriteOverviewWin = load("FavoriteOverview.fxml");
        messagesOverviewWin = load("MessagesOverview.fxml");
        sendMessageWin = load("SendMessage.fxml");
        viewMessagesWin = load("MyMessagesOverview.fxml");
        alertOverviewWin = load("AlertOverview.fxml");
        createCityWin = load("CreateCity.fxml");
        myBookings = load("MyBookings.fxml");
        bookingRequests = load("BookingRequests.fxml");
        logOutWin = load("DoubleCheck.fxml");
        videoWin = load("video.fxml");
        ((MainGUIController)mainWin.c).initializeButtonVisibility();

        showMain(stage);
    }


    public Stage getStage() {
        return stage;
    }

    private void showMain(Stage stage) {
        // set stage's scene
        Scene scene = new Scene(mainWin.ui);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("ShareTrip");
        stage.setScene(scene);
        stage.setHeight(944.0);
        stage.setWidth(1512.0);
        stage.show();
    }

    public void showUpdate(Stage stage, Double width, Double height, Boolean fullScreen) {
        if (fullScreen){
            stage.setFullScreen(true);
        }else{
            stage.setHeight(height);
            stage.setWidth(width);
        }
        stage.show();

    }

    public void searchFavRide(City from, City to) {
        mainWrapper.setCenter(queryRidesWin.ui);
        ((QueryRidesController)queryRidesWin.c).resetValues();
        ((QueryRidesController)queryRidesWin.c).searchFavRide(from, to);
        ((QueryRidesController)queryRidesWin.c).searchRides(null);

    }

    public static void main(String[] args) {
       launch();
    }

    public void setUserName(String label) {
        ((MainGUIController)mainWin.c).setUserName(label);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        ((MainGUIController)mainWin.c).setIsLoggedIn(isLoggedIn);
    }
}
