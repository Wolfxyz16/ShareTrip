module eus.ehu.sharetrip {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires org.apache.logging.log4j;
    requires jakarta.persistence;
    requires jbcrypt;

    opens eus.ehu.sharetrip.domain to javafx.base, org.hibernate.orm.core;
    opens eus.ehu.sharetrip.ui to javafx.fxml;
    opens eus.ehu.sharetrip.uicontrollers to javafx.fxml;
    opens eus.ehu.sharetrip.utils to javafx.fxml;
    exports eus.ehu.sharetrip.ui;
}
