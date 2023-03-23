package com.example.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movietime.Activity.FilmActivity;
import com.example.movietime.Activity.GenreActivity;
import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmWithGenre;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Film> filmList = new ArrayList<>();
    private List<FilmWithGenre> filmWithGenresList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void findFilm(View view) {
    }


    public void displayFilms(View view) {
        Intent intent = new Intent(MainActivity.this, FilmActivity.class);
        startActivity(intent);
    }

    public void displayGenres(View view) {
        Intent intent = new Intent(MainActivity.this, GenreActivity.class);
        startActivity(intent);
    }
}
