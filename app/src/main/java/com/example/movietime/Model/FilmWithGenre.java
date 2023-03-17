package com.example.movietime.Model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class FilmWithGenre {
    @Embedded
    public Film film;
    @Relation(
            parentColumn = "filmId",
            entityColumn = "genreId",
            associateBy = @Junction(FilmGenreCrossRef.class)
    )
    public List<Genre> genres;

    @Override
    @NonNull
    public String toString(){
        return this.film.toString() + " | " + this.genres.toString();
    }
}
