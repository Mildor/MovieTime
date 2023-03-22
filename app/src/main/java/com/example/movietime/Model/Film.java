package com.example.movietime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Film implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long filmId;

    private String titre;

    @Nullable
    private String description;

    private String dateDeSortie;

    private Double rating;

    public Film() {
    }

    public Film(String titre, @Nullable String description, String dateDeSortie, Double rating) {
        this.titre = titre;
        this.description = description;
        this.dateDeSortie = dateDeSortie;
        this.rating = rating;
    }

    protected Film(Parcel in) {
        filmId = in.readLong();
        titre = in.readString();
        description = in.readString();
        dateDeSortie = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(filmId);
        parcel.writeString(titre);
        parcel.writeString(description);
        parcel.writeString(dateDeSortie);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(rating);
        }
    }
}