package com.example.movietime.Tools;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Model.Film;
import com.example.movietime.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {
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
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder holder, int position) {
        Film film = localData.get(position);
        holder.display(film);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView fvtitre, fvrating;
        //private Task myTask;


        private MyViewHolder(View itemView){
            super(itemView);
            fvtitre = itemView.findViewById(R.id.fvtitre);
            fvrating = itemView.findViewById(R.id.fvrating);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void display(Film film){
            fvtitre.setText(film.getTitre());
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
