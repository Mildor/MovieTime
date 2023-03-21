package com.example.movietime;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.Genre;
import com.example.movietime.Tools.ViewModel.GenreViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FilmActivity extends AppCompatActivity {
    private TextView tvTitre;
    private TextView tvDesc;
    private GenreViewModel genreViewModel;
    // initialize variables
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    ArrayList<Integer> genreIds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_film);
        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        Intent newIntent = getIntent();

        if (newIntent != null){
            Film film = newIntent.getParcelableExtra("film");
            if (film != null ){
                EditText edtTitre = findViewById(R.id.edtTitre);
                edtTitre.setText(film.getTitre());
                EditText edtDesc = findViewById(R.id.edtDescription);
                edtDesc.setText(film.getDescription());
            }
        }



        genreViewModel.getAllGenres().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(List<Genre> genres) {
                int id = 0;
                String[] langArray = new String[genres.size()];
                Integer[] idGenres = new Integer[genres.size()];
                for ( Genre element : genres){
                    langArray[id] = element.getLibelle();
                    idGenres[id] = element.getGenreId();
                    id++;
                }
                // assign variable
                textView = findViewById(R.id.genres);

                // initialize selected language array
                selectedLanguage = new boolean[langArray.length];

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Initialize alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(FilmActivity.this);

                        // set title
                        builder.setTitle("Choisis tes genres");

                        // set dialog non cancelable
                        builder.setCancelable(false);

                        builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                // check condition
                                if (b) {
                                    // when checkbox selected
                                    // Add position  in lang list
                                    langList.add(i);
                                    // Sort array list
                                    Collections.sort(langList);
                                } else {
                                    // when checkbox unselected
                                    // Remove position from langList
                                    langList.remove(Integer.valueOf(i));
                                }
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                genreIds.clear();
                                // Initialize string builder
                                StringBuilder stringBuilder = new StringBuilder();
                                // use for loop
                                for (int j = 0; j < langList.size(); j++) {
                                    // concat array value
                                    stringBuilder.append(langArray[langList.get(j)]);
                                    genreIds.add(idGenres[langList.get(j)]);
                                    // check condition
                                    if (j != langList.size() - 1) {
                                        // When j value  not equal
                                        // to lang list size - 1
                                        // add comma
                                        stringBuilder.append(", ");
                                    }
                                }
                                // set text on textView
                                textView.setText(stringBuilder.toString());
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dismiss dialog
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // use for loop
                                for (int j = 0; j < selectedLanguage.length; j++) {
                                    // remove all selection
                                    selectedLanguage[j] = false;
                                    // clear language list
                                    langList.clear();
                                    // clear text view value
                                    textView.setText("");
                                }
                            }
                        });
                        // show dialog
                        builder.show();
                    }
                });
            }
        });

    }

    public void register(View view) {
        Date currentTime = Calendar.getInstance().getTime();
        EditText edtTitre = findViewById(R.id.edtTitre);
        EditText edtDesc = findViewById(R.id.edtDescription);
        Intent newIntent = getIntent();
        Intent intent = new Intent(view.getContext(), MainActivity.class);

        if (newIntent != null){
            Film film = newIntent.getParcelableExtra("film");
            if (film != null ){
                film.setTitre(edtTitre.getText().toString());
                film.setDescription(edtDesc.getText().toString());

                intent.putIntegerArrayListExtra("genres", genreIds);
                intent.putExtra("filmM", film);

                startActivity(intent);
            }else{
                Film filmNew = new Film(
                        edtTitre.getText().toString(),
                        edtDesc.getText().toString(),
                        currentTime.toString(),
                        0.0
                );

                intent.putIntegerArrayListExtra("genres", genreIds);
                intent.putExtra("filmC", filmNew);

                startActivity(intent);
            }
        }
    }
}

