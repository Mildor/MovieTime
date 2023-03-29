package com.example.movietime.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.MainActivity;
import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Model.Genre;
import com.example.movietime.R;
import com.example.movietime.Tools.FilmAdapter;
import com.example.movietime.Tools.InterfaceMyListener;
import com.example.movietime.Tools.ViewModel.FilmViewModel;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmActivity extends AppCompatActivity {
    private List<Film> filmList = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private List<FilmWithGenre> filmWithGenresList;

    private List<Genre> genres;
    private List<Film> trieFilm= new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recyclerView = findViewById(R.id.listFilm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FilmAdapter filmAdapter = new FilmAdapter(filmList);
        recyclerView.setAdapter(filmAdapter);

        FilmViewModel filmViewModel = new ViewModelProvider(this).get(FilmViewModel.class);
        GenreViewModel genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

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
                filmList.addAll(films);
                filmAdapter.storeList(films);
                filmAdapter.notifyDataSetChanged();
            }
        });

        genreViewModel.getAllGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> allGenres) {
                List<Genre> genresToAdapter = new ArrayList<>();
                genresToAdapter.add(new Genre(""));
                genresToAdapter.addAll(allGenres);
                ArrayAdapter genreAdapter = new ArrayAdapter(FilmActivity.this, R.layout.spinner, genresToAdapter);

                Spinner genreSpinner = findViewById(R.id.genre);

                genreSpinner.setAdapter(genreAdapter);

                genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (trieFilm != null){
                            trieFilm.clear();
                        }
                        Genre genre = (Genre) parent.getSelectedItem();

                        for (FilmWithGenre filmGenre : filmWithGenresList){
                            for (Genre genre1 : filmGenre.genres){
                                if (Objects.equals(genre.getGenreId(), genre1.getGenreId())){
                                    Log.d("voyonsvoir", filmGenre.film.toString());
                                    trieFilm.add(filmGenre.film);
                                }
                            }
                        }
                        if (trieFilm != null && trieFilm.size() != 0){
                            filmAdapter.storeList(trieFilm);
                            filmAdapter.notifyDataSetChanged();
                        }else{
                            filmAdapter.storeList(filmList);
                            filmAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

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
                List<Genre> listGenres = new ArrayList<>();
                Intent intent = new Intent(FilmActivity.this, FilmCreationActivity.class);
                for( FilmWithGenre element : filmWithGenresList){
                    if (Objects.equals(element.film.getFilmId(), film.getFilmId())){
                        listGenres.addAll(element.genres);
                    }
                }
                intent.putExtra("genres", (Serializable) listGenres);
                intent.putExtra("film", film);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSearchRequested() {
//        pauseSomeStuff();
        return super.onSearchRequested();
    }
    public void retourMain(View view){
        Intent intentGenre = new Intent(FilmActivity.this, MainActivity.class);
        FilmActivity.this.startActivity(intentGenre);
    }
    public void addFilm(View view) {
        Intent intentFilm = new Intent(FilmActivity.this, FilmCreationActivity.class);
        FilmActivity.this.startActivity(intentFilm);
    }
    public void associerFilmGenre(View view) {
        Intent intentGenreFilm = new Intent(FilmActivity.this, RelationGenreFilmActivity.class);
        FilmActivity.this.startActivity(intentGenreFilm);
    }
}
