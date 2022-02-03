package org.yellowtree.moviebrowse.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import org.yellowtree.moviebrowse.model.Movie
import org.yellowtree.moviebrowse.repository.SearchAPIServiceRepo

class DetailViewModel(app :Application) : AndroidViewModel(app) {

    private val searchAPIServiceRepo = SearchAPIServiceRepo.getInstance()


    private val _id = MutableLiveData<String>()

    val movieLiveData = _id.switchMap { id ->
        searchAPIServiceRepo.find(getApplication(), id)
    }
    private val _detailLiveData = MutableLiveData<Movie>()


    fun fetchMovie(id : String) {
        _id.value = id
    }

}