package com.example.filmaficionado;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FilmController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}