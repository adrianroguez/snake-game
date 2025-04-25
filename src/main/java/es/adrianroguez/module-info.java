module es.adrianroguez.ahorcado {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.sql;

    opens es.adrianroguez to javafx.fxml;

    exports es.adrianroguez;
    exports es.adrianroguez.controller;

    opens es.adrianroguez.controller to javafx.fxml;
    opens es.adrianroguez.controller.abstracts to javafx.fxml;
}
