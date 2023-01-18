package com.example.filmaficionado;

import java.sql.SQLException;
import java.util.List;

public interface FilmDAOInter {



    public void updateFilm();

    public void updateFilm(Film[] film);

    public void addFilm(Film film);

    public void sletFilm(Film film);

    public void updateFilmInfo(Film film, String filmInfo);

    public String getTrailerName(Film film);
}
