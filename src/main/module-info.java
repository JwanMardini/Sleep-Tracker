module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires org.junit.jupiter.api;

    opens app to javafx.fxml;


    exports app;

}
