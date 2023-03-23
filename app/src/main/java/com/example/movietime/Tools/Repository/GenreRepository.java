package com.example.movietime.Tools.Repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.movietime.Model.Genre;
import com.example.movietime.Model.GenreWithFilm;
import com.example.movietime.Tools.Dao.GenreDao;
import com.example.movietime.Tools.FilmRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenreRepository {

    private GenreDao genreDao;
    private LiveData<List<Genre>> allGenres;
    private LiveData<Genre> selectedGenre;
    private Integer genreId;

    private List<GenreWithFilm> genresWithFilms;

    public GenreRepository(Application app) {
        FilmRoomDatabase db = FilmRoomDatabase.getDataBase(app);
        genreDao = db.genreDao();
//        this.genreId = genreId;
        allGenres = genreDao.getAllGenreLD();
//        selectedGenre = genreDao.getSelectedGenre(genreId);
//        genresWithFilms = genreDao.getGenresWithFilms();
    }

    public LiveData<List<Genre>> getAllGenres(){ return allGenres;}

    public void insert(Genre g){ new InsertThread(genreDao).execute(g);}
    public void delete(Genre g){ new InsertThread(genreDao).executeDelete(g);}
    public void update(Genre g){ new InsertThread(genreDao).executeUpdate(g);}

    public void insertAll(List<Genre> g){ new InsertThread(genreDao).executeAll(g);}

    public LiveData<Genre> getSelectedGenre(Integer genreId){ return selectedGenre;}

    public List<GenreWithFilm> getGenresWithFilms(){ return genresWithFilms;}

    private static class InsertThread{
        private final GenreDao genreDao;
        ExecutorService executorService;
        Handler handler;

        public InsertThread(GenreDao g){
            genreDao = g;
            executorService = Executors.newSingleThreadExecutor();
            handler = new Handler(Looper.getMainLooper());
        }

        public void execute(Genre genre){
            executorService.execute(new Runnable() {
                @Override
                public void run() { genreDao.insert(genre);
                }
            });
        }

        public void executeUpdate(Genre genre){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    genreDao.update(genre);
                }
            });
        }

        public void executeAll(List<Genre> genres){
            executorService.execute(new Runnable() {
                @Override
                public void run() {genreDao.insertAllGenres(genres);
                }
            });
        }

        public void executeDelete(Genre genre) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    genreDao.delete(genre);
                }
            });
        }
    }
}
