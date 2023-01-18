package com.example.filmaficionado;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Film {
    String title,filmInfo;
    int anmeldelse,id;

    ArrayList<String> genre;

    public Film(String title, List<String> genre, String filmInfo, int anmeldelse, int id){
        this.title = title;
        this.genre = new ArrayList<>(genre);
        this.filmInfo = filmInfo;
        this.anmeldelse = anmeldelse;
        this.id = id;
    }

    public int getId(){return id;}


    public String getTitle(){return title;}

    public ArrayList<String> getGenre(){return genre;}

    public String getFilmInfo(){return filmInfo;}

   public int getAnmeldelse(){return anmeldelse;}


    public void setInfo(String info) {filmInfo = info;}
}
