package com.example.filmaficionado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class KategoriDAOImpl implements KategoriDAOInter {



    @Override
    public void editKategori(Kategori kategori, String newName){}

    public List getAllKategori(){

        List category = new ArrayList<>();

        try( Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
             Statement statement = con.createStatement();){

            ResultSet resultSet = null;

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Category;";
            resultSet = statement.executeQuery(selectSql);


            // Print results from select statement
            while (resultSet.next()) {
                category.add(new Kategori(resultSet.getInt("id"), resultSet.getString("CatName")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return category;


    }

    @Override
    public void addKategori(Kategori kategori) {
        try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;")){
            PreparedStatement updateCategory = con.prepareStatement("INSERT INTO Category(CatName) VALUES (\'" + kategori.getName() + "\');");
            updateCategory.executeUpdate();


        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void updateKategori() {

    }

    @Override
    public void deleteFilmFromKategori(Kategori kategori, com.example.filmaficionado.Film film){

    }

    @Override
    public void addFilmToKategori(Kategori kategori, com.example.filmaficionado.Film film){

    }

    @Override
    public List<Film> getKategoriFilm(Kategori kategori){
        List<Film> categoryMedMovie = new ArrayList<>();

        try( Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
             Statement statement = con.createStatement();
             Statement statementMovieGenre = con.createStatement();){

            ResultSet resultSet;

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM CatMovie, Movie WHERE Movie.id = CatMovie.MovieId AND CatMovie.CategoryId = " + kategori.id;
            resultSet = statement.executeQuery(selectSql);


            // Print results from select statement
            while (resultSet.next()) {
                selectSql = "SELECT MovieId, CatName FROM CatMovie, Category WHERE CatMovie.MovieId = "+resultSet.getInt("MovieId")+" AND Category.id = CatMovie.CategoryId";
                ResultSet resultSetMovieGenre = statementMovieGenre.executeQuery(selectSql);
                List<String> movieGenre = new ArrayList<>();
                while (resultSetMovieGenre.next()){
                    movieGenre.add(resultSetMovieGenre.getString("CatName"));
                }
                categoryMedMovie.add(new Film(resultSet.getString("MovieName"), movieGenre, resultSet.getString("MovieInfo"), resultSet.getInt("Rating"),resultSet.getInt("MovieId")));

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return categoryMedMovie;

    }

    @Override
    public void sletKategori(Kategori kategori) {

        try (
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=filmDB;user=test1;password=test1;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
                PreparedStatement updateCategory = con.prepareStatement("DELETE FROM Category WHERE CatName=\'" + kategori.getName() + "\';");
                PreparedStatement updateCatMovie = con.prepareStatement("DELETE FROM CatMovie WHERE CategoryId = " + kategori.getId() + ";");
                PreparedStatement updateMovie = con.prepareStatement("DELETE FROM Movie WHERE id NOT IN(SELECT MovieId FROM CatMovie);");
        ) {
            updateCatMovie.executeUpdate();
            updateCategory.executeUpdate();
            updateMovie.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
