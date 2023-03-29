package com.example.movietime.Tools.Repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Tools.Dao.FilmDao;
import com.example.movietime.Tools.FilmRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilmRepository {
    private FilmDao filmDao;
    private static long id;
    private LiveData<List<Film>> allfilms;
    private LiveData<List<Film>> searchedFilms;
    private LiveData<Integer> getLastIdFilm;
    private final LiveData<List<FilmWithGenre>> filmWithGenres;
    private String searchedTitre;

    public FilmRepository(Application application) {
        FilmRoomDatabase db = FilmRoomDatabase.getDataBase(application);
        filmDao = db.filmDao();
        allfilms = filmDao.getAllFilmLD();
        searchedFilms = filmDao.getSearchFilm(searchedTitre);
        getLastIdFilm = filmDao.getLastIdFilm();
        filmWithGenres = filmDao.getFilmsWithGenres();
    }

    public LiveData<List<Film>> getAllfilms(){ return allfilms;}

    public LiveData<List<Film>> getSearchedFilm(String searchedTitre){return filmDao.getSearchFilm(searchedTitre);}

    public LiveData<List<FilmWithGenre>> getFilmWithGenres(){ return filmWithGenres;}

    public long insert(Film f){ return new InsertThread(filmDao).execute(f);}
    public void delete(Film f){ new InsertThread(filmDao).executeDelete(f);}
    public void update(Film f){ new InsertThread(filmDao).executeUpdate(f);}
    public void insertCrossRef(FilmGenreCrossRef filmGenreCrossRef){ new InsertThread(filmDao).executeCrossRef(filmGenreCrossRef);}
    public void deleteCrossRef(FilmGenreCrossRef filmGenreCrossRef){ new InsertThread(filmDao).executeDeleteCrossRef(filmGenreCrossRef);}

    public void insertAll(List<Film> f){ new InsertThread(filmDao).executeAll(f);}

    public LiveData<Integer> getLastIdFilm(){ return getLastIdFilm;}

    private static class InsertThread{
        private final FilmDao filmDao;
        ExecutorService executorService;
        Handler handler;

        public InsertThread(FilmDao f){
            filmDao = f;
            executorService = Executors.newSingleThreadExecutor();
            handler = new Handler(Looper.getMainLooper());
        }

        public void executeUpdate(Film film){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    filmDao.update(film);
                }
            });
        }

        public long execute(Film film){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    id = filmDao.insert(film);
                }
            });
            return id;
        }

        public void executeCrossRef(FilmGenreCrossRef filmGenreCrossRef){
            executorService.execute(new Runnable() {
                @Override
                public void run() { filmDao.insertCrossRef(filmGenreCrossRef);
                }
            });
        }

        public void executeDeleteCrossRef(FilmGenreCrossRef filmGenreCrossRef){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    filmDao.deleteCrossRef(filmGenreCrossRef);
                }
            });
        }

        public void executeAll(List<Film> films){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    filmDao.insertAllFilms(films);
                }
            });
        }

        public void executeDelete(Film film) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    filmDao.delete(film);
                }
            });
        }
    }
}
