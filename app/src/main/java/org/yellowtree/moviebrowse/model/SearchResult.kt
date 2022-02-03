package org.yellowtree.moviebrowse.model

import androidx.room.Entity
import org.yellowtree.moviebrowse.repository.SearchAPIServiceRepo

@Entity(
    tableName = "searchResult",
    primaryKeys = ["id"]

)
data class SearchResult(val id : String, val title : String, val year : String,
                        val type : String, val posterUrl : String)

