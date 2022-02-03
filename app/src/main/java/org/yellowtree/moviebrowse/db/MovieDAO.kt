package org.yellowtree.moviebrowse.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow
import org.yellowtree.moviebrowse.model.SearchResult

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite : SearchResult)


    @Query("SELECT * FROM searchResult")
    fun fetchFavorites() : Flow<List<SearchResult>>

    @Delete
    fun removeFavorite(favorite: SearchResult)
}