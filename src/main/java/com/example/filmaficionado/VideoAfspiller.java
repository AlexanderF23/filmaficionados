package com.example.filmaficionado;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class VideoAfspiller {


    private final static String pathToTrailer = "C:/Users/alexa/Desktop/Filmaficionadoprogram/Filmaficionado/src/main/resources/FilmTrailer/" ;

    public static void seTrailerTilFilm(String filNavn) {
        String path = pathToTrailer + filNavn;
        try {
            File media = new File(path);
            Desktop.getDesktop().open(media);
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
