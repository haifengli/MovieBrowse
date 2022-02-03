package org.yellowtree.moviebrowse.ui

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.yellowtree.moviebrowse.db.MovieDB
import org.yellowtree.moviebrowse.model.Movie
import org.yellowtree.moviebrowse.model.SearchResult
import org.yellowtree.moviebrowse.repository.SearchAPIServiceRepo
import org.yellowtree.moviebrowse.util.AbsentLiveData
import org.yellowtree.moviebrowse.util.Resource
import java.util.*

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val _query = MutableLiveData<String>()

    private val searchAPIServiceRepo = SearchAPIServiceRepo.getInstance()

    private val movieDB = MovieDB.getInstance(app)


    val favorites : LiveData<List<SearchResult>> = movieDB.movieDAO().fetchFavorites().asLiveData()

    val favIds = favorites.map { favs ->
        favs.map{ fav ->
            fav.id
        }
    }

    val results : LiveData<Resource<List<SearchResult>>> = _query.switchMap { query ->
        if (query.isBlank()) {
            AbsentLiveData.create()
        } else {
            searchAPIServiceRepo.search(app, query)
        }

    }

    fun fetchMovie(id : String) : LiveData<Resource<Movie>> {
        return searchAPIServiceRepo.find(getApplication(), id)
    }

    fun setQuery(input : String) {
        val queryString = input.toLowerCase(Locale.getDefault()).trim()
        if(queryString == _query.value) {
            return
        }

        _query.value  = queryString
    }

    fun deleteFav(fav: SearchResult) {
        viewModelScope.launch {
            favOpr(fav, false)
        }
    }

    fun insertFav(fav: SearchResult) {
        viewModelScope.launch {
            favOpr(fav, true)
        }
    }

    private suspend fun favOpr(fav : SearchResult, isInsert : Boolean) {
        withContext(Dispatchers.IO) {
            if (isInsert) {
                movieDB.movieDAO().insertFavorite(fav)
            } else {
                movieDB.movieDAO().removeFavorite(fav)
            }
        }
    }




}