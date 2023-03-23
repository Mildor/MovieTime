package com.example.movietime.Tools.ViewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Tools.Repository.FilmRepository;

import java.util.List;
public class FilmViewModel extends AndroidViewModel {
    private final FilmRepository filmRepository;
    private LiveData<List<Film>> allFilms;
    private LiveData<Integer> lastIdFilm;
    private LiveData<Film> selectedFilm;
    private LiveData<List<FilmWithGenre>> filmWithGenres;

    public FilmViewModel(Application app){
        super(app);
        filmRepository = new FilmRepository(app);
        LiveData<List<Film>> allFilms = filmRepository.getAllfilms();
//        LiveData<Integer> lastIdFilm = filmRepository.getLastIdFilm();
//        LiveData<Film> selectedFilm = filmRepository.getSelectedFilm(filmId);
        LiveData<List<FilmWithGenre>> filmWithGenres = filmRepository.getFilmWithGenres();
    }

    public long insert(Film film){ return filmRepository.insert(film);}
    public void delete(Film film){ filmRepository.delete(film);}
    public void update(Film film){ filmRepository.update(film);}

    public void insertCrossRef(FilmGenreCrossRef filmGenreCrossRef){ filmRepository.insertCrossRef(filmGenreCrossRef);}

    public void deleteCrossRef(FilmGenreCrossRef filmGenreCrossRef){ filmRepository.deleteCrossRef(filmGenreCrossRef);}

    public void insertAll(List<Film> films){ filmRepository.insertAll(films);}

    public LiveData<List<Film>> getAllFilms(){return filmRepository.getAllfilms();}

    public LiveData<Integer> getLastIdFilm(){return filmRepository.getLastIdFilm();}

    public LiveData<Film> getSelectedFilm(Integer filmId){return filmRepository.getSelectedFilm(filmId);}

    public LiveData<List<FilmWithGenre>> filmWithGenres(){ return filmRepository.getFilmWithGenres();}
}
