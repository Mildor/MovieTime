package com.example.movietime.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class Film {

    @PrimaryKey
    private int filmId;

    private String titre;

    @Nullable
    private String description;

    private String dateDeSortie;

    private Double rating;

    public Film() {
    }

    public Film(int id, String titre, @Nullable String description, String dateDeSortie, Double rating) {
        this.filmId = id;
        this.titre = titre;
        this.description = description;
        this.dateDeSortie = dateDeSortie;
        this.rating = rating;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public String getDateDeSortie() {
        return dateDeSortie;
    }

    public void setDateDeSortie(String dateDeSortie) {
        this.dateDeSortie = dateDeSortie;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString(){
        return "Film:" +
                "id=" + filmId +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateDeSortie=" + dateDeSortie +
                ", rating=" + rating;
    }
}