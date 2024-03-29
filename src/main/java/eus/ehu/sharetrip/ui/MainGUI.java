package eus.ehu.sharetrip.ui;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import eus.ehu.sharetrip.uicontrollers.Controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MainGUI {

    private BlFacade businessLogic;

    @FXML
    private BorderPane mainWrapper;


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
                init(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private Window mainWin, createRideWin, queryRidesWin, loginWin, registerWin, favoriteOverviewWin, chatOverviewWin, alertOverviewWin;

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
            case "View Alert" -> mainWrapper.setCenter(alertOverviewWin.ui);
            case "View Messages" -> mainWrapper.setCenter(chatOverviewWin.ui);
            case "View Favorites" -> mainWrapper.setCenter(favoriteOverviewWin.ui);
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Log in" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
        }
    }


    public void init(Stage stage) throws IOException {

        mainWin = load("MainGUI.fxml");
        createRideWin = load("CreateRide.fxml");
        queryRidesWin = load("QueryRides.fxml");
        loginWin = load("SignIn.fxml");
        registerWin = load("SignUp.fxml");
        favoriteOverviewWin = load("FavoriteOverview.fxml");
        chatOverviewWin = load("ChatOverview.fxml");
        alertOverviewWin = load("AlertOverview.fxml");

        showMain(stage);
    }

    private void showMain(Stage stage) {
        mainWrapper.setCenter(mainWin.ui);

        stage.setTitle("ShareTrip BorderLayout");
        stage.setHeight(740.0);
        stage.setWidth(1200.0);

        stage.show();
    }

    public static void main(String[] args) {
       launch();
    }

    public void setLabel(String label) {

    }
}
