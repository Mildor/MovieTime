package com.example.movietime.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.MainActivity;
import com.example.movietime.Model.FilmWithGenre;
import com.example.movietime.Model.Genre;
import com.example.movietime.R;
import com.example.movietime.Tools.GenreAdapter;
import com.example.movietime.Tools.InterfaceMyListener;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.util.ArrayList;
import java.util.List;

public class GenreActivity extends AppCompatActivity {
    private List<Genre> genreList = new ArrayList<>();
    private List<FilmWithGenre> filmWithGenresList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recyclerView = findViewById(R.id.listGenre);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        GenreAdapter genreAdapter = new GenreAdapter(genreList);
        recyclerView.setAdapter(genreAdapter);



        GenreViewModel genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        genreViewModel.getAllGenres().observe(this, new Observer<List<Genre>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Genre> genres) {
                genreAdapter.storeList(genres);
                genreAdapter.notifyDataSetChanged();
            }
        });

        genreAdapter.setMyListener(new InterfaceMyListener() {
            @Override
            public void onItemClick(int position, View v) {
                Genre genre = GenreAdapter.getData().get(position);
                Intent intent = new Intent(GenreActivity.this, GenreCreationActivity.class);
                intent.putExtra("genre", genre);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View view) {
                Genre genre = GenreAdapter.getData().get(position);
                genreViewModel.delete(genre);
            }
        });
    }

    public void addGenre(View view) {
        Intent intentGenre = new Intent(GenreActivity.this, GenreCreationActivity.class);
        GenreActivity.this.startActivity(intentGenre);
    }

    public void retourMain(View view){
        Intent intentGenre = new Intent(GenreActivity.this, MainActivity.class);
        GenreActivity.this.startActivity(intentGenre);
    }

    public void associerFilmGenre(View view) {
        Intent intentGenreFilm = new Intent(GenreActivity.this, RelationGenreFilmActivity.class);
        GenreActivity.this.startActivity(intentGenreFilm);
    }
}
