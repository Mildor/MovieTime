package com.example.movietime.Tools.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movietime.Model.Genre;
import com.example.movietime.Model.GenreWithFilm;
import com.example.movietime.Tools.Repository.GenreRepository;

import java.util.List;

public class GenreViewModel extends AndroidViewModel {

    private GenreRepository genreRepository;
    private LiveData<List<Genre>> allGenres;
    private LiveData<Genre> getSelectedGenre;
    private List<GenreWithFilm> genreWithFilms;

    public GenreViewModel(@NonNull Application app){
        super(app);
        genreRepository = new GenreRepository(app);
        LiveData<List<Genre>> allGenres = genreRepository.getAllGenres();
//        LiveData<Genre> selectedGenre = genreRepository.getSelectedGenre(genreId);
//        List<GenreWithFilm> genreWithFilms = genreRepository.getGenresWithFilms();
    }

    public void insert(Genre genre){genreRepository.insert(genre);}

    public void insertAll(List<Genre> genres){ genreRepository.insertAll(genres);}

    public LiveData<List<Genre>> getAllGenres(){ return genreRepository.getAllGenres();}

    public LiveData<Genre> getSelectedGenre(Integer genreId){ return genreRepository.getSelectedGenre(genreId);}

    public List<GenreWithFilm> getGenreWithFilms(){ return genreRepository.getGenresWithFilms();}
}
