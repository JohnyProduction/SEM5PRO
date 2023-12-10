module com.projektsemv.clubmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.projektsemv.clubmanagement to javafx.fxml;
    exports com.projektsemv.clubmanagement;
    exports com.projektsemv.clubmanagement.manager;
    opens com.projektsemv.clubmanagement.manager to javafx.fxml;
    exports com.projektsemv.clubmanagement.fan;
    opens com.projektsemv.clubmanagement.fan to javafx.fxml;
}