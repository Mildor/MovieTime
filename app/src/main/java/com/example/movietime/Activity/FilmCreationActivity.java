package com.example.movietime.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.Genre;
import com.example.movietime.R;
import com.example.movietime.Tools.ViewModel.FilmViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilmCreationActivity extends AppCompatActivity {
    private FilmViewModel filmViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_film);


        Intent newIntent = getIntent();

        if (newIntent != null){
            List<Genre> genres = (List<Genre>) newIntent.getSerializableExtra("genres");
            if (genres != null){for (Genre genre : genres) {
                    Log.d("Genres", genre.toString());
                }
            }
            Film film = newIntent.getParcelableExtra("film");
            if (film != null ){
                EditText edtTitre = findViewById(R.id.edtTitre);
                edtTitre.setText(film.getTitre());
                EditText edtDesc = findViewById(R.id.edtDescription);
                edtDesc.setText(film.getDescription());
            }
        }

    }

    public void register(View view) {
        Date currentTime = Calendar.getInstance().getTime();
        EditText edtTitre = findViewById(R.id.edtTitre);
        EditText edtDesc = findViewById(R.id.edtDescription);
        Intent UpdateIntent = getIntent();
        Intent intent = new Intent(view.getContext(), FilmActivity.class);

        filmViewModel = new ViewModelProvider(this).get(FilmViewModel.class);


        if (UpdateIntent != null){
            Film film = UpdateIntent.getParcelableExtra("film");
            if (film != null ){
                film.setTitre(edtTitre.getText().toString());
                film.setDescription(edtDesc.getText().toString());
                filmViewModel.update(film);
            }else{
                Film filmNew = new Film();
                filmNew.setTitre(edtTitre.getText().toString());
                filmNew.setDescription(edtDesc.getText().toString());
                filmNew.setRating(0.0);
                filmNew.setDateDeSortie(currentTime.toString());
                filmViewModel.insert(filmNew);
            }
            startActivity(intent);
        }
    }
}

