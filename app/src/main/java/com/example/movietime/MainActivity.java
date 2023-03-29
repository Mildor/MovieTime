package com.example.movietime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietime.Activity.FilmActivity;
import com.example.movietime.Activity.GenreActivity;
import com.example.movietime.Model.Film;
import com.example.movietime.Tools.ViewModel.FilmViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private List<Film> filmList = new ArrayList<>();
    private List<Film> filmListB = new ArrayList<>();
    private FilmViewModel filmViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void findFilm(View view) {
        view.setEnabled(false);
        URL url = createURL();
        if (url!=null){
            FilmTMDB filmTMDB = new FilmTMDB(this);
            filmTMDB.execute(url);

        }
        else{
            Toast.makeText(this,"URL invalide",Toast.LENGTH_LONG).show();
        }
    }

    private URL createURL(){
        String apiKEY = "23dd51f50530d7b7baed38dfa5e9db44";
        String baseUrl = "https://api.themoviedb.org/3/movie/popular?api_key=";

        try {
            String urlString = baseUrl + apiKEY + "&language=fr-FR";
            Log.d("urlConstruct", urlString);
            return new URL(urlString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null; //URL mal formée
    }

    public void displayFilms(View view) {
        Intent intent = new Intent(MainActivity.this, FilmActivity.class);
        startActivity(intent);
    }

    public void displayGenres(View view) {
        Intent intent = new Intent(MainActivity.this, GenreActivity.class);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class FilmTMDB extends AsyncTask<URL, Void, JSONObject> {
        private final Context context;

        public FilmTMDB(Context c){context = c;}

        @Override
        protected JSONObject doInBackground(URL... params){
            HttpURLConnection connection = null;
            try {
                connection = (HttpsURLConnection)params[0].openConnection();
                Log.d("connection",connection.toString());
                connection.setConnectTimeout(5000);
                int reponse = connection.getResponseCode();
                if (reponse == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();

                    try(BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    )){
                        String line;
                        while((line = reader.readLine()) != null){
                            builder.append(line);
                        }
                    }catch (IOException e){
                        runOnUiThread((Runnable) ()->{
                            Toast.makeText(getApplicationContext(), "probleme lecture donnees", Toast.LENGTH_LONG).show();
                        });
                        e.printStackTrace();
                    }
                    return  new JSONObject(builder.toString());
                }else {
                    runOnUiThread((Runnable) ()->{
                        Toast.makeText(getApplicationContext(), "probleme lecture donnees", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (IOException | JSONException e) {
                runOnUiThread((Runnable) ()->{
                    Toast.makeText(getApplicationContext(), "probleme lecture donnees", Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            } finally {
                assert connection != null;
                connection.disconnect();
            }
            return null;
        }

        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        @Override
        protected void onPostExecute(JSONObject film){
            filmViewModel = new ViewModelProvider(MainActivity.this).get(FilmViewModel.class);
            TextView textView = findViewById(R.id.tvTitre);
            if(film!=null) {
                try {
                    convertJSONtoArrayList(film);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "MAJ effectuee", Toast.LENGTH_LONG).show();
                filmViewModel.insertAll(filmList);
            }
            else{
                Toast.makeText(getApplicationContext(), "probleme MAJ", Toast.LENGTH_LONG).show();
                textView.setText("pb MAJ");
            }
        }
    }

    private void convertJSONtoArrayList(JSONObject object) throws JSONException {
        JSONArray list = object.getJSONArray("results");

        for(int i=0;i<list.length();i++){
            JSONObject all_films = list.getJSONObject(i);

            String dateString = all_films.getString("release_date");
            filmList.add(new Film(
                    all_films.getString("title"),
                    all_films.getString("overview"),
                    dateString,
                    all_films.getDouble("vote_average")
            ));
        }
    }
}
