package com.example.movietime.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Genre implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int genreId;
    private String libelle;

    @Ignore
    public Genre() {
    }

    public Genre(String libelle) {
        this.libelle = libelle;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return "Genre:" +
                "id=" + genreId +
                ", libelle='" + libelle + '\'' +
                 '}';
    }
}
