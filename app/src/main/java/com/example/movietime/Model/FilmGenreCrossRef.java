package com.example.movietime.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"filmId", "genreId"})
public class FilmGenreCrossRef {
    public long filmId;
    public long genreId;

    @NonNull
    @Override
    public String toString(){
        return this.filmId +" "+this.genreId;
    }
}
