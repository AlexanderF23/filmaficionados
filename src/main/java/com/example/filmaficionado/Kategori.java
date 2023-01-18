package com.example.filmaficionado;


import java.util.LinkedList;
public class Kategori {

    int id;

    String name,BestMovie;



    LinkedList<Film> filmList;

        public Kategori(int id, String name, LinkedList<Film> filmList){
            this.id = id;
            this.name = name;
            this.filmList = filmList;
        }

        public Kategori(int id, String name){
            this.id = id;
            this.name = name;

            this.filmList = new LinkedList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name){this.name = name;}

        public LinkedList<Film> getFilmlist(){return filmList;}

        public void setFilmlist(LinkedList<Film> filmlist){this.filmList = filmlist;}

        public void addFilm(Film film) {this.filmList.add(film);}

        public String getNumberOfFilm(){return String.valueOf(filmList.size());}

        @Override
        public String toString() {return name;}

        public int getId(){return id;}
}
