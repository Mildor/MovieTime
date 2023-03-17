package com.example.movietime.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class GenreWithFilm {
    @Embedded public Genre genre;
    @Relation(
            parentColumn = "genreId",
            entityColumn = "filmId",
            associateBy = @Junction(FilmGenreCrossRef.class)
    )
    public List<Film> films;
}
