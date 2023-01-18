package com.example.filmaficionado;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;

import java.sql.SQLException;

import java.util.*;
import java.util.List;


public class FilmController implements Initializable {
    @FXML
    private TitledPane Tableviewfilm;

    @FXML
    private TitledPane TableviewfilmInfo;

    @FXML
    private TitledPane Tableviewkategorier;

    @FXML
    private AnchorPane anchorTableviewfilmKasse;

    @FXML
    private AnchorPane anchortableviewkategorierKasse;

    @FXML
    private AnchorPane anchortextareafilmInfoKasse;

    @FXML
    private Button redigerFilm;

    @FXML
    private Button seTrailer;

    @FXML
    private Button sletFilm;

    @FXML
    private Button sletKategori;

    @FXML
    private TextField søgFilm;

    @FXML
    private TableView<Kategori> tabelviewKategoriKasse;

    @FXML
    private TableColumn<Kategori, String> tableviewColFilmKasse;

    @FXML
    private TableColumn<Kategori, String> tableviewColKategoriKasse;

    @FXML
    private TableView<Film> tableviewFilmKasse;

    @FXML
    private TextArea textareaFilmInfoKasse;

    @FXML
    private Button tilføjFilm;

    @FXML
    private Button tilføjKategorier;




    private final FilmDAOInter filmDAOInter = new FilmDAOImp();

    private final KategoriDAOInter katDAOInter = new KategoriDAOImpl();

    private LinkedList<Film> allFilmlist;
    private LinkedList<Kategori> allKategoriList;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allKategoriList = new LinkedList<>();
        allFilmlist = new LinkedList<>();


        tableviewColKategoriKasse.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableviewColFilmKasse.setCellValueFactory(new PropertyValueFactory<>("title"));


        KategoriDAOInter kategoriDAOInter = new KategoriDAOImpl();

        allKategoriList = new LinkedList<>(kategoriDAOInter.getAllKategori());

        int lowRatingIndex = 0;
        int index = 0;
        for (Kategori kategori : allKategoriList) {
            LinkedList<Film> filmInThisKategori = new LinkedList<>(kategoriDAOInter.getKategoriFilm(kategori));
            for (Film film : filmInThisKategori) {
                kategori.addFilm(film);
            }

            if(kategori.getName().equals("Low Rating")){
                lowRatingIndex=index;
            }
            index++;
        }

        tabelviewKategoriKasse.getItems().addAll(allKategoriList);

        ObservableList<Film> data = tableviewFilmKasse.getItems();
        søgFilm.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())){
                tableviewFilmKasse.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Film> subentries = FXCollections.observableArrayList();

            long count = tableviewFilmKasse.getColumns().size();
            for (int i = 0; i < tableviewFilmKasse.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableviewFilmKasse.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)){
                        subentries.add(tableviewFilmKasse.getItems().get(i));
                        break;
                    }
                }
            }
            tableviewFilmKasse.setItems(subentries);
        });

        tabelviewKategoriKasse.requestFocus();
        tabelviewKategoriKasse.getSelectionModel().select(lowRatingIndex);
        tabelviewKategoriKasse.getFocusModel().focus(lowRatingIndex);

        updateUI();
    }

    public void updateUI(){

        Kategori selectedKategori = tabelviewKategoriKasse.getSelectionModel().getSelectedItem();
        int selectedKategoriIndex = tabelviewKategoriKasse.getSelectionModel().getSelectedIndex();
        Film addFilm = tableviewFilmKasse.getSelectionModel().getSelectedItem();

        updateTabeleviewKategori();

        if (selectedKategori != null) {

            tabelviewKategoriKasse.getSelectionModel().select(selectedKategoriIndex);
            tabelviewKategoriKasse.getSelectionModel().focus(selectedKategoriIndex);

            for (Film film : selectedKategori.getFilmlist()){
                tableviewFilmKasse.getItems().add(film);
            }

            if (addFilm != null){
                tableviewFilmKasse.scrollTo(addFilm);
            }

            updatetextareaFilmInfoKasse();
        }
        int selectedFilmIndex = tableviewFilmKasse.getSelectionModel().getSelectedIndex();
        tableviewFilmKasse.getItems().clear();


        tabelviewKategoriKasse.getSelectionModel().select(selectedKategoriIndex);
        tabelviewKategoriKasse.getSelectionModel().focus(selectedKategoriIndex);

        Kategori currentKategori = tabelviewKategoriKasse.getSelectionModel().getSelectedItem();

        if (currentKategori != null){

            tableviewFilmKasse.getItems().addAll(currentKategori.getFilmlist());
            tableviewFilmKasse.scrollTo(addFilm);

        }
        tableviewFilmKasse.getSelectionModel().select(selectedFilmIndex);
        tableviewFilmKasse.getSelectionModel().focus(selectedFilmIndex);

    }

    @FXML
    public void updateTabeleviewKategori(){
        tabelviewKategoriKasse.getItems().clear();

        for (Kategori kategori : katDAOInter.getAllKategori()) {
            tabelviewKategoriKasse.getItems().add(kategori);
            LinkedList<Film> filmInThisKategori = new LinkedList<>(katDAOInter.getKategoriFilm(kategori));
            for (Film film : filmInThisKategori) {
                kategori.addFilm(film);
            }
        }

    }


    @FXML
    public void updatetextareaFilmInfoKasse(){
        textareaFilmInfoKasse.getText();
         Film currentFilm = tableviewFilmKasse.getSelectionModel().getSelectedItem();

        if (currentFilm !=null){
            String text = String.format("Beskrivelse:\n%s\n\n",currentFilm.getFilmInfo());
            text += String.format( "Anmeldelse: %d\n\n",currentFilm.getAnmeldelse());
            text += "Genre: ";
            text += String.join(", ", currentFilm.getGenre());
            textareaFilmInfoKasse.setText(text);
        }
    }


    @FXML
    public void updateOnMouseClick(MouseEvent event){
        updateUI();
    }

    @FXML
    public void tilføjKategorier(MouseEvent event) throws IOException, SQLException {


        Stage primaryStage = new Stage();

        Label kategoriLable = new Label("Navn på ny Kategori");
        TextField kategoriTF = new TextField();
        Button kategoriBTN = new Button("Add");

        HBox root = new HBox();
        root.setPadding(new Insets(50, 50,50,50));
        root.setSpacing(20);
        root.setAlignment(Pos.BASELINE_CENTER);
        root.getChildren().addAll(kategoriLable, kategoriTF, kategoriBTN);
        Scene scene = new Scene(root, 450, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tilføj ny Kategori");
        primaryStage.show();

        kategoriBTN.setOnAction(e ->
        {
            String userInput = kategoriTF.getText();

            Kategori newKategori = new Kategori(-1, userInput);
            katDAOInter.addKategori(newKategori);

            updateUI();

            primaryStage.close();
        });

    }

    @FXML
    public void tilføjFilm(MouseEvent event) throws IOException, SQLException {
        Stage primaryStage = new Stage();

        Label titleLabel = new Label("Navn på ny film");
        Label categoryLabel = new Label("Filmens genre");
        Label infoLabel = new Label("Info om film");
        Label scoreLabel = new Label("Film score");
        TextField titleTF = new TextField();
        TextField categoryTF = new TextField();
        ComboBox categoryBox = new ComboBox();
        for (Kategori kategori : katDAOInter.getAllKategori()) {
            categoryBox.getItems().add(kategori.getName());
        }
        TextField infoTF = new TextField();
        ComboBox scoreBox = new ComboBox();

        for(int option = 1; option <= 10; option++){
            scoreBox.getItems().add(option);
        }
        Button filmBTN = new Button("Add");

        VBox root = new VBox(10);//Spacing;
        root.setPadding(new Insets(50, 150,50,150));
        root.setAlignment(Pos.BASELINE_CENTER);
        root.getChildren().addAll(titleLabel, titleTF, categoryLabel, categoryBox, infoLabel, infoTF, scoreLabel, scoreBox,  filmBTN);
        Scene scene = new Scene(root, 450, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tilføj ny film");
        primaryStage.show();

        filmBTN.setOnAction(e ->
        {
            String title = titleTF.getText();
            List<String> category = new ArrayList<>();
            category.add((String) categoryBox.getValue());
            String info = infoTF.getText();
            int score = (int) scoreBox.getValue();


            Film newFilm = new Film(title, category, info, score,-1);
            filmDAOInter.addFilm(newFilm);

            updateUI();

            primaryStage.close();
        });
    }

    @FXML
    public void sletFilm(MouseEvent event) {
        Film filmToDelete = tableviewFilmKasse.getSelectionModel().getSelectedItem();
        filmDAOInter.sletFilm(filmToDelete);
        updateUI();
    }

    @FXML
    public void sletKategori(MouseEvent event) {
        Kategori kategoriToDelete = tabelviewKategoriKasse.getSelectionModel().getSelectedItem();
        katDAOInter.sletKategori(kategoriToDelete);

        updateUI();
    }

    @FXML
    public void redigerFilmInfo(MouseEvent event){
        Film filmToEdit = tableviewFilmKasse.getSelectionModel().getSelectedItem();
        String info = textareaFilmInfoKasse.getText();
        filmDAOInter.updateFilmInfo(filmToEdit, info);
        filmToEdit.setInfo(info);
        updateUI();
    }

    @FXML
    public void afspilValgtFilm(MouseEvent event){
        Film currentFilm = tableviewFilmKasse.getSelectionModel().getSelectedItem();
        String trailerName = filmDAOInter.getTrailerName(currentFilm);
        if (trailerName != null){
            VideoAfspiller.seTrailerTilFilm(trailerName);
        }
    }
}



