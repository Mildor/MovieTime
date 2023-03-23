package com.example.movietime.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Genre implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long genreId;
    private String libelle;

    @Ignore
    public Genre() {
    }

    public Genre(String libelle) {
        this.libelle = libelle;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @NonNull
    @Override
    public String toString(){
        return "Genre:" +
                "id=" + genreId +
                ", libelle='" + libelle + '\'' +
                 '}';
    }
}
