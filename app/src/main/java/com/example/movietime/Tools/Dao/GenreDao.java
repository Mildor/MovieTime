package com.example.movietime.Tools.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movietime.Model.Genre;
import com.example.movietime.Model.GenreWithFilm;

import java.util.List;
@Dao
public interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Genre genre);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllGenres(List<Genre> genres);

    @Query("SELECT * FROM Genre ORDER BY libelle ASC;")
    LiveData<List<Genre>> getAllGenreLD();

//    @Query("SELECT * FROM Genre WHERE genreId = :id;")
//    LiveData<Genre> getSelectedGenre(int id);

    @Transaction
    @Query("SELECT * FROM Genre")
    public List<GenreWithFilm> getGenresWithFilms();

}
