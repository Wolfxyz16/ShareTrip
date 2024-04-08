package eus.ehu.sharetrip.ui;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.uicontrollers.MainGUIController;
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

    private Window mainWin, createRideWin, queryRidesWin, loginWin, registerWin, favoriteOverviewWin, chatOverviewWin, alertOverviewWin, createCityWin, messagesOverviewWin, sendMessageWin, viewMessagesWin;

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
            case "View Alert" -> mainWrapper.setCenter(alertOverviewWin.ui);
            case "Message Overview" -> mainWrapper.setCenter(messagesOverviewWin.ui);
            case "Send message" -> mainWrapper.setCenter(sendMessageWin.ui);
            case "View messages" -> mainWrapper.setCenter(viewMessagesWin.ui);
            case "View Favorites" -> mainWrapper.setCenter(favoriteOverviewWin.ui);
            case "Query Rides" -> mainWrapper.setCenter(queryRidesWin.ui);
            case "Create Ride" -> mainWrapper.setCenter(createRideWin.ui);
            case "Log in" -> mainWrapper.setCenter(loginWin.ui);
            case "Register" -> mainWrapper.setCenter(registerWin.ui);
            case "Create City" -> mainWrapper.setCenter(createCityWin.ui);


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



        ((MainGUIController)mainWin.c).initializeButtonVisibility();

        showMain(stage);
    }

    private void showMain(Stage stage) {

        // set stage's scene
        Scene scene = new Scene(mainWin.ui);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.setTitle("ShareTrip BorderLayout");
        stage.setHeight(740.0);
        stage.setWidth(1200.0);

        stage.show();
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
