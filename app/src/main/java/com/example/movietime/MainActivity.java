package com.example.movietime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Model.Genre;
import com.example.movietime.Tools.FilmAdapter;
import com.example.movietime.Tools.ViewModel.FilmViewModel;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<Film> filmList;
    private List<FilmWithGenre> filmWithGenresList;
    private int id = 0;
    private FilmViewModel filmViewModel;
    private GenreViewModel genreViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        FilmAdapter filmAdapter = new FilmAdapter(filmList);
        recyclerView.setAdapter(filmAdapter);

        filmAdapter.setMyListener(((position, v) -> {
            StringBuilder allGenresofFilms = new StringBuilder();
            Film film = FilmAdapter.getData().get(position);
            int filmId = film.getFilmId();
            for( FilmWithGenre element : filmWithGenresList){
                if (element.film.getFilmId() == film.getFilmId()){
                    allGenresofFilms.append(film.getDescription());
                }
            }
            new AlertDialog.Builder(v.getContext())
                    .setTitle(film.getTitre())
                    .setMessage(allGenresofFilms)
                    .show();
        }));

        filmViewModel = new ViewModelProvider(this).get(FilmViewModel.class);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        filmViewModel.getAllFilms().observe(this, new Observer<List<Film>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Film> films) {
                filmAdapter.storeList(films);
                filmAdapter.notifyDataSetChanged();
            }
        });

        filmViewModel.filmWithGenres().observe(this, new Observer<List<FilmWithGenre>>() {
            @Override
            public void onChanged(List<FilmWithGenre> filmWithGenres) {
                filmWithGenresList = filmWithGenres;
            }
        });

        Intent intent = getIntent();

        ArrayList<Integer> genres = intent.getIntegerArrayListExtra("genres");

        String filmTitre = intent.getStringExtra("titre");
        String filmDesc = intent.getStringExtra("description");

        if( filmTitre != null && filmDesc != null){
            Date currentTime = Calendar.getInstance().getTime();
            Film newFilm = new Film(
                    150,
                    filmTitre,
                    filmDesc,
                    currentTime.toString(),
                    0.0
            );
            filmViewModel.insert(newFilm);

            if (genres != null){
                for (Integer genre : genres) {
                    associer(newFilm.getFilmId(), genre);
                }
            }

        }

        String libelleGenre = intent.getStringExtra("libelle");

        if( libelleGenre != null){
            Genre newGenre = new Genre(150, libelleGenre);
            genreViewModel.insert(newGenre);
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

    public void associer(Integer filmId, Integer genreId) {
        FilmGenreCrossRef filmGenreCrossRef = new FilmGenreCrossRef();
        filmGenreCrossRef.filmId = filmId;
        filmGenreCrossRef.genreId = genreId;

        filmViewModel.insertCrossRef(filmGenreCrossRef);
    }

}
