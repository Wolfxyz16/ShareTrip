package eus.ehu.sharetrip.uicontrollers;

import eus.ehu.sharetrip.businessLogic.BlFacade;
import eus.ehu.sharetrip.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class videoController implements Controller {

    @FXML
    private MediaView video;
    private File file;
    private Media media;

    private MediaPlayer mediaPlayer;

    @FXML
    private Button stopBtn;

    @FXML
    private Button playBtn;

    private MainGUI mainGUI;
    private final BlFacade bl;


    public videoController(BlFacade bl) {
        this.bl = bl;
    }


    @FXML
    public void initialize() {
        file = new File("src/main/resources/eus/ehu/sharetrip/ui/assets/video.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        video.setMediaPlayer(mediaPlayer);
        visibleVideo();

    }

    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public void stop(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    public void play(ActionEvent actionEvent) {
        visibleVideo();
        mediaPlayer.play();
    }

    public void visibleVideo(){
        video.setVisible(true);
    }
}
