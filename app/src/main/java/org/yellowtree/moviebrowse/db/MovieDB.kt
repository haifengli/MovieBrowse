package org.yellowtree.moviebrowse.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.yellowtree.moviebrowse.model.SearchResult

@Database(
    entities = [SearchResult::class],
    version = 1,
    exportSchema = false

)
abstract class MovieDB : RoomDatabase(){
    abstract fun movieDAO() : MovieDAO


    companion object {
        private val INSTANCE : MovieDB? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, MovieDB::class.java, "movie.db")
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}