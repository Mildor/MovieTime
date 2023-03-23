package com.example.movietime.Tools.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.FilmWithGenre;

import java.util.List;
@Dao
public interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Film film);

    @Update
    void update(Film film);

    @Delete
    void delete(Film film);
    @Insert
    void insertAllFilms(List<Film> films);
    @Query("SELECT * from Film ORDER BY titre ASC;")
    LiveData<List<Film>> getAllFilmLD();

    @Query("SELECT filmId FROM Film ORDER BY filmId DESC LIMIT 1;")
    LiveData<Integer> getLastIdFilm();

    @Transaction
    @Query("SELECT * FROM Film")
    public LiveData<List<FilmWithGenre>> getFilmsWithGenres();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCrossRef(FilmGenreCrossRef filmGenreCrossRef);

    @Delete
    void deleteCrossRef(FilmGenreCrossRef filmGenreCrossRef);
}
