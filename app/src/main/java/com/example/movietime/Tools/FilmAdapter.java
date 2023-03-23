package com.example.movietime.Tools;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Model.Film;
import com.example.movietime.R;
import com.example.movietime.Tools.ViewModel.FilmViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder>  {
    private static List<Film> localData = new ArrayList<>();

    private static InterfaceMyListener myListener;

    public static InterfaceMyListener getMyListener(){ return myListener;}

    public void setMyListener(InterfaceMyListener myListener){
        FilmAdapter.myListener = myListener;
    }

    public FilmAdapter(List<Film> l) { localData = l;}

    public static List<Film> getData(){ return localData;}

    @Override
    public int getItemCount() {
        if (localData != null){
            return localData.size();
        }else{
            return 0;
        }
    }

    public void storeList(List<Film> filmList){ localData = filmList;}

    @NonNull
    @Override
    public FilmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder holder, int position) {
        Film film = localData.get(position);
        holder.display(film);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView fvtitre, fvrating, fvdate;
        private Button deleteButton;
        private Film film;
        private FilmViewModel filmViewModel= new FilmViewModel(new Application());

        private MyViewHolder(View itemView){
            super(itemView);
            fvtitre = itemView.findViewById(R.id.fvtitre);
            fvrating = itemView.findViewById(R.id.fvrating);
            fvdate = itemView.findViewById(R.id.fvdateDeSortie);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Film film = localData.get(getAdapterPosition());
                    filmViewModel.delete(film);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void display(Film film){
            fvtitre.setText(film.getTitre());
            fvdate.setText(film.getDateDeSortie());
            Double NoRating = 0.0;
            if (Objects.equals(film.getRating(), NoRating)){
                fvrating.setText("Non noté");
            }else{
                fvrating.setText(film.getRating().toString());
            }

        }

        @Override
        public void onClick(View view) {
            myListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            myListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }
}
