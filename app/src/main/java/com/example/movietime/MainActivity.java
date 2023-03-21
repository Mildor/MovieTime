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
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Model.Genre;
import com.example.movietime.Tools.FilmAdapter;
import com.example.movietime.Tools.InterfaceMyListener;
import com.example.movietime.Tools.ViewModel.FilmViewModel;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

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
                Intent intent = new Intent(MainActivity.this, FilmActivity.class);
//                intent.putExtra("FilmTitle", film.getTitre());
//                intent.putExtra("FilmDesc", film.getDescription());
//                intent.putExtra("FilmDate", film.getDateDeSortie());
//                intent.putExtra("FilmRating", film.getRating());
//                intent.putExtra("FilmId", film.getFilmId());
                intent.putExtra("film", film);
                startActivity(intent);
            }
        });

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

//        Intent intent = getIntent();
//

//
//        String filmTitre = intent.getStringExtra("titre");
//        String filmDesc = intent.getStringExtra("description");
//        int filmId = intent.getIntExtra("filmId", 0);
//
//        if( filmTitre != null && filmDesc != null){
//            if ( filmId != 0){
//                Date currentTime = Calendar.getInstance().getTime();
//                for (int i = 0; i < filmAdapter.getItemCount(); i++) {
//                    if (filmAdapter.getData().get(i).getFilmId() == filmId){
//                        Log.d("dfsqmfd", String.valueOf(filmId));
//                        Film film = filmAdapter.getData().get(i);
//                        film.setTitre(filmTitre);
//                        film.setDescription(filmDesc);
//                        filmViewModel.update(film);

//                    }
//                }
//
//            }else{
//                Date currentTime = Calendar.getInstance().getTime();
//                Film newFilm = new Film(
//                        filmTitre,
//                        filmDesc,
//                        currentTime.toString(),
//                        0.0
//                );
//                filmViewModel.insert(newFilm);
//                if (genres != null){
//                    for (Integer genre : genres) {
//                        associer(newFilm.getFilmId(), genre);
//                    }
//                }
//            }
//        }

        Intent intent = getIntent();

        if( intent != null){
            ArrayList<Integer> genres = intent.getIntegerArrayListExtra("genres");
            Film filmNew = intent.getParcelableExtra("filmC");
            Film filmModify = intent.getParcelableExtra("filmM");

            if (filmNew != null){
                Log.d("create", filmNew.toString());
                filmViewModel.insert(filmNew);
                if (genres != null){
                    for (Integer genre : genres) {
                        Log.d("Genres", String.valueOf(genre));
                        associer(filmNew.getFilmId(), genre);
                    }
                }
            }else if(filmModify != null){
                Log.d("modify", filmModify.toString());
                filmViewModel.update(filmModify);
                if (genres != null){
                    for (Integer genre : genres) {
                        Log.d("Genres", String.valueOf(genre));
                        associer(filmModify.getFilmId(), genre);
                    }
                }
            }
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

    public void associer(Integer filmId, Integer genreId) {
        FilmGenreCrossRef filmGenreCrossRef = new FilmGenreCrossRef();
        filmGenreCrossRef.filmId = filmId;
        filmGenreCrossRef.genreId = genreId;

        filmViewModel.insertCrossRef(filmGenreCrossRef);
    }

}
