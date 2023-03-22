package com.example.movietime.Tools;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietime.MainActivity;
import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.Genre;
import com.example.movietime.R;
import com.example.movietime.Tools.ViewModel.FilmViewModel;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddGenreToFilmActivity extends AppCompatActivity {
    private GenreViewModel genreViewModel;
    private FilmViewModel filmViewModel;
    TextView textViewGenre;
    TextView textViewFilm;
    boolean[] selectedGenres;
    boolean[] selectedFilms;
    ArrayList<Integer> genreList = new ArrayList<>();
    ArrayList<Integer> filmList = new ArrayList<>();
    ArrayList<Integer> genreIds = new ArrayList<>();
    ArrayList<Long> filmIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_genre_film);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);
        filmViewModel = new ViewModelProvider(this).get(FilmViewModel.class);

        filmViewModel.getAllFilms().observe(this, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                int id = 0;
                String[] filmArray = new String[films.size()];
                Long[] idFilms = new Long[films.size()];
                for ( Film element : films){
                    filmArray[id] = element.getTitre();
                    idFilms[id] = element.getFilmId();
                    id++;
                }
                textViewFilm = findViewById(R.id.films);
                selectedFilms = new boolean[filmArray.length];

                textViewFilm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddGenreToFilmActivity.this);
                        builder.setTitle("Choisis tes genres");
                        builder.setCancelable(false);

                        builder.setMultiChoiceItems(filmArray, selectedFilms, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                if (b) {
                                    filmList.add(i);
                                    Collections.sort(filmList);
                                } else {
                                    filmList.remove(Integer.valueOf(i));
                                }
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                filmIds.clear();
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int j = 0; j < filmList.size(); j++) {
                                    stringBuilder.append(filmArray[filmList.get(j)]);
                                    filmIds.add(idFilms[filmList.get(j)]);
                                    if (j != filmList.size() - 1) {
                                        stringBuilder.append(", ");
                                    }
                                }
                                textViewFilm.setText(stringBuilder.toString());
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j < selectedFilms.length; j++) {
                                    selectedFilms[j] = false;
                                    filmList.clear();
                                    textViewFilm.setText("");
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        });

        genreViewModel.getAllGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                int id = 0;
                String[] genreArray = new String[genres.size()];
                Integer[] idGenres = new Integer[genres.size()];
                for ( Genre element : genres){
                    genreArray[id] = element.getLibelle();
                    idGenres[id] = element.getGenreId();
                    id++;
                }
                textViewGenre = findViewById(R.id.genres);
                selectedGenres = new boolean[genreArray.length];

                textViewGenre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddGenreToFilmActivity.this);
                        builder.setTitle("Choisis tes genres");
                        builder.setCancelable(false);

                        builder.setMultiChoiceItems(genreArray, selectedGenres, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                if (b) {
                                    genreList.add(i);
                                    Collections.sort(genreList);
                                } else {
                                    genreList.remove(Integer.valueOf(i));
                                }
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                genreIds.clear();
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int j = 0; j < genreList.size(); j++) {
                                    stringBuilder.append(genreArray[genreList.get(j)]);
                                    genreIds.add(idGenres[genreList.get(j)]);
                                    if (j != genreList.size() - 1) {
                                        stringBuilder.append(", ");
                                    }
                                }
                                textViewGenre.setText(stringBuilder.toString());
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j < selectedGenres.length; j++) {
                                    selectedGenres[j] = false;
                                    genreList.clear();
                                    textViewGenre.setText("");
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        });
    }

    public void associer(Long filmId, Integer genreId) {
        FilmGenreCrossRef filmGenreCrossRef = new FilmGenreCrossRef();
        filmGenreCrossRef.filmId = filmId;
        filmGenreCrossRef.genreId = genreId;
        Log.d("associer", filmGenreCrossRef.toString());
        filmViewModel.insertCrossRef(filmGenreCrossRef);
    }

    public void register(View view){
        Log.d("films", filmIds.toString());
        Log.d("genres", genreIds.toString());

        int lengthFilms = filmIds.size();
        int lengthGenres = genreIds.size();

        if (lengthFilms <= lengthGenres){
            for(Integer genreId : genreIds){
                for (Long filmId : filmIds){
                    associer(filmId, genreId);
                }
            }
        }else{
            for(Long filmId : filmIds){
                for (Integer genreId : genreIds){
                    associer(filmId, genreId);
                }
            }
        }
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
