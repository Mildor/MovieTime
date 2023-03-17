package com.example.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GenreActivity extends AppCompatActivity {
    private TextView tvLibelle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_genre);
    }


    public void register(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        EditText edtLibelle = findViewById(R.id.edtLibelle);
        intent.putExtra("libelle", edtLibelle.getText().toString());
        startActivity(intent);
    }
}
