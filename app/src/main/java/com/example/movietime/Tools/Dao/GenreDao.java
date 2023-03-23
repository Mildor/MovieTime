package com.example.movietime.Tools.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.movietime.Model.Genre;
import com.example.movietime.Model.GenreWithFilm;

import java.util.List;
@Dao
public interface GenreDao {

    @Insert
    void insert(Genre genre);
    @Delete
    void delete(Genre genre);
    @Update
    void update(Genre genre);

    @Insert
    void insertAllGenres(List<Genre> genres);

    @Query("SELECT * FROM Genre ORDER BY libelle ASC;")
    LiveData<List<Genre>> getAllGenreLD();

    @Transaction
    @Query("SELECT * FROM Genre")
    public List<GenreWithFilm> getGenresWithFilms();

}
