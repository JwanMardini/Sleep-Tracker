module app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires org.junit.jupiter.api;


    opens app to javafx.fxml;

    exports app;

}
