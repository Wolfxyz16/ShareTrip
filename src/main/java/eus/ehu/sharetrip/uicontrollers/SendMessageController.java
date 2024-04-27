package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.domain.Message;
import eus.ehu.sharetrip.domain.User;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SendMessageController implements Controller{


    @FXML
    private Label errorLabel;

    @FXML
    private TextArea txtMessage;

    @FXML
    private TextField txtRecipient;

    @FXML
    public Button backBtn2;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
    public SendMessageController(BlFacade bl) {
        businessLogic = bl;
    }
    @FXML
    void sendMessage(ActionEvent event) {
        String recipient = txtRecipient.getText();
        String messageContent = txtMessage.getText();

        if (recipient.isEmpty() || messageContent.isEmpty()) {
            System.out.println("Recipient and/or message cannot be empty.");
            errorLabel.setText("Recipient and/or message cannot be empty.");
            errorLabel.getStyleClass().setAll("label", "lbl-danger");
            return;
        }

        User receiver = businessLogic.getUser(recipient);
        User sender = businessLogic.getCurrentUser();

        if (receiver == null) {
            System.out.println("Recipient does not exist.");
            errorLabel.setText("Recipient does not exist.");
            errorLabel.getStyleClass().setAll("label", "lbl-danger");
            return;
        }

        Message message = new Message.Builder()
                .messageText(messageContent)
                .sender(sender)
                .receiver(receiver)
                .build();

        businessLogic.saveMessage(message);

        System.out.println("Message sent successfully.");
        errorLabel.setText("Message sent successfully.");
        errorLabel.getStyleClass().setAll("label", "lbl-success");
    }


    public void backToMessageController(ActionEvent actionEvent) {
        mainGUI.showScene("Message Overview");
    }
}
