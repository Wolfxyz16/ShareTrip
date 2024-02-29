module eus.ehu.sharetrip {
    requires javafx.controls;
    requires javafx.fxml;


    opens eus.ehu.sharetrip to javafx.fxml;
    exports eus.ehu.sharetrip;
}