module com.example.filmaficionado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.filmaficionado to javafx.fxml;
    exports com.example.filmaficionado;
}