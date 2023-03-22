package com.example.movietime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Model.Genre;
import com.example.movietime.Tools.AddGenreToFilmActivity;
import com.example.movietime.Tools.FilmAdapter;
import com.example.movietime.Tools.InterfaceMyListener;
import com.example.movietime.Tools.ViewModel.FilmViewModel;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<Film> filmList;
    private List<FilmWithGenre> filmWithGenresList;
    private int id = 0;
    private FilmViewModel filmViewModel;
    private GenreViewModel genreViewModel;

    private int lastId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FilmAdapter filmAdapter = new FilmAdapter(filmList);
        recyclerView.setAdapter(filmAdapter);

        filmAdapter.setMyListener(new InterfaceMyListener() {
            @Override
            public void onItemClick(int position, View v) {
                Film film = FilmAdapter.getData().get(position);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(film.getTitre())
                        .setMessage(film.getDescription())
                        .show();
            }

            @Override
            public void onItemLongClick(int position, View view) {
                Film film = FilmAdapter.getData().get(position);
                Log.d("filmLongClick", film.toString());
                List<Genre> listGenres = new ArrayList<>();
                Intent intent = new Intent(MainActivity.this, FilmActivity.class);
                for( FilmWithGenre element : filmWithGenresList){
                    if (element.film.getFilmId() == film.getFilmId()){
                        listGenres.addAll(element.genres);
                    }
                }
                intent.putExtra("genres", (Serializable) listGenres);
                intent.putExtra("film", film);
                startActivity(intent);
            }
        });

        filmViewModel = new ViewModelProvider(this).get(FilmViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        filmViewModel.filmWithGenres().observe(this, new Observer<List<FilmWithGenre>>() {
            @Override
            public void onChanged(List<FilmWithGenre> filmWithGenres) {
                filmWithGenresList = filmWithGenres;
            }
        });
        filmViewModel.getAllFilms().observe(this, new Observer<List<Film>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Film> films) {
                filmAdapter.storeList(films);
                filmAdapter.notifyDataSetChanged();
            }
        });

        Intent intent = getIntent();

        if( intent != null){

            String libelleGenre = intent.getStringExtra("libelle");

            if( libelleGenre != null){
                Genre newGenre = new Genre(libelleGenre);
                genreViewModel.insert(newGenre);
            }
        }
    }

    public void addFilm(View view) {
        Intent intentFilm = new Intent(MainActivity.this, FilmActivity.class);
        MainActivity.this.startActivity(intentFilm);
    }

    public void findFilm(View view) {
    }

    public void addGenre(View view) {
        Intent intentGenre = new Intent(MainActivity.this, GenreActivity.class);
        MainActivity.this.startActivity(intentGenre);
    }

    public void associerFilmGenre(View view) {
        Intent intentGenreFilm = new Intent(MainActivity.this, AddGenreToFilmActivity.class);
        MainActivity.this.startActivity(intentGenreFilm);
    }
}
