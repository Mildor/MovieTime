package com.example.movietime.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietime.Model.Genre;
import com.example.movietime.R;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

public class GenreCreationActivity extends AppCompatActivity {
    private EditText edtLibelle;
    private GenreViewModel genreViewModel;

    private Genre genre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_genre);
        Intent intent = getIntent();
        edtLibelle = findViewById(R.id.edtLibelle);
        if (intent != null){
            genre = (Genre) intent.getSerializableExtra("genre");
            if (genre != null){
                edtLibelle.setText(genre.getLibelle());
            }
        }
    }


    public void register(View view) {
        Intent intent = new Intent(view.getContext(), GenreActivity.class);
        Intent updateIntent = getIntent();

        edtLibelle = findViewById(R.id.edtLibelle);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        if (updateIntent != null){
            if (genre != null ){
                Log.d("genre", genre.toString());
                genre.setLibelle(edtLibelle.getText().toString());
                genreViewModel.update(genre);
            }else{
                Genre newGenre = new Genre();
                newGenre.setLibelle(edtLibelle.getText().toString());
                genreViewModel.insert(newGenre);
            }
            startActivity(intent);
        }
    }
}
