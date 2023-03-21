package com.example.movietime.Tools;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movietime.Model.Film;
import com.example.movietime.Model.FilmGenreCrossRef;
import com.example.movietime.Model.Genre;
import com.example.movietime.Tools.Dao.FilmDao;
import com.example.movietime.Tools.Dao.GenreDao;

@Database(entities = {Film.class, Genre.class, FilmGenreCrossRef.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class FilmRoomDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();
    public abstract GenreDao genreDao();

    private static volatile FilmRoomDatabase INSTANCE;

    public static FilmRoomDatabase getDataBase(final Context context){
        if(INSTANCE == null ){
            synchronized (FilmRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FilmRoomDatabase.class, "film_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
