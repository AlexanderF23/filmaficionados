package com.example.filmaficionado;



import java.util.List;
public interface KategoriDAOInter {

    public void addKategori(Kategori kategori);

    public void editKategori(Kategori kategori, String newName);

    public List<Kategori> getAllKategori();

    public void updateKategori();

    public void deleteFilmFromKategori(Kategori kategori, Film film);

    public void addFilmToKategori(Kategori kategori, Film film);

    public List<Film> getKategoriFilm(Kategori kategori);

    public void sletKategori(Kategori kategori);
}
