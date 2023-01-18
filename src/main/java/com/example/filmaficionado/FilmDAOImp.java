package com.example.filmaficionado;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
public class FilmDAOImp implements FilmDAOInter {


    public void addFilm(Film film) {
        String title = film.getTitle();
        String info = film.getFilmInfo();
        List<String> genreList = film.getGenre();
        int anmeldelse = film.getAnmeldelse();
        try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")){



             PreparedStatement updateMovie = con.prepareStatement("INSERT INTO Movie(MovieName, Rating, MovieInfo) VALUES (\'" + title + "\',\' " + anmeldelse +"\',\'" + info + "\');");
             updateMovie.executeUpdate();

             PreparedStatement getNextId = con.prepareStatement("SELECT IDENT_CURRENT(\'Movie\');");
             ResultSet nextIdResult = getNextId.executeQuery();

             nextIdResult.next();

             int movieId = nextIdResult.getInt(1);

             for(String genre : genreList){



                 PreparedStatement queryCategory = con.prepareStatement("SELECT id FROM Category WHERE CatName = \'" + genre + "\';");
                 ResultSet queryResult = queryCategory.executeQuery();

                 queryResult.next();
                 int categoryId = queryResult.getInt("id");

                 PreparedStatement ps2 = con.prepareStatement("INSERT INTO CatMovie (CategoryId, MovieId) VALUES ("+ categoryId + "," + movieId +");");

                 ps2.executeUpdate();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    @Override
    public void sletFilm(Film film){
        try (
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
                PreparedStatement updateMovie = con.prepareStatement("DELETE FROM Movie WHERE id=" + film.getId() + ";");
                PreparedStatement updateCatMovie = con.prepareStatement("DELETE FROM CatMovie WHERE MovieId = " + film.getId() + ";");

        ) {
            updateCatMovie.executeUpdate();
            updateMovie.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateFilm() {

    }

    @Override
    public void updateFilm(Film[] film) {

    }

    @Override
    public void updateFilmInfo(Film film, String info){
        try (
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
                PreparedStatement updateMovie = con.prepareStatement("UPDATE Movie SET MovieInfo =\'" + info + "\' WHERE id = " + film.getId() + ";");

        ) {

            updateMovie.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTrailerName(Film film) {
        String trailerName = null;
        try (
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
                PreparedStatement queryMovie = con.prepareStatement("SELECT FilLink FROM Movie WHERE id = " + film.getId() + ";");

        ) {

            ResultSet result = queryMovie.executeQuery();
            result.next();
            trailerName = result.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trailerName;
    }


}













