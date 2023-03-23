package com.example.movietime.Tools;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietime.Model.Genre;
import com.example.movietime.R;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder>  {
    private static List<Genre> localData = new ArrayList<>();

    private static InterfaceMyListener myListener;

    public static InterfaceMyListener getMyListener(){ return myListener;}

    public void setMyListener(InterfaceMyListener myListener){
        GenreAdapter.myListener = myListener;
    }

    public GenreAdapter(List<Genre> l) { localData = l;}

    public static List<Genre> getData(){ return localData;}

    @Override
    public int getItemCount() {
        if (localData != null){
            return localData.size();
        }else{
            return 0;
        }
    }

    public void storeList(List<Genre> genreList){ localData = genreList;}

    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_layout, parent, false);

        return new GenreAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.MyViewHolder holder, int position) {
        Genre genre = localData.get(position);
        holder.display(genre);
    }
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView fvtitre, fvid;
        //private Task myTask;


        private MyViewHolder(View itemView){
            super(itemView);
            fvtitre = itemView.findViewById(R.id.fvtitre);
            fvid = itemView.findViewById(R.id.fvid);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void display(Genre genre){
            fvtitre.setText(genre.getLibelle());
            fvid.setText(String.valueOf(genre.getGenreId()));
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
