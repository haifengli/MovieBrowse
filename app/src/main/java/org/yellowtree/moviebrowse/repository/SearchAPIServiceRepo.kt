package org.yellowtree.moviebrowse.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import org.yellowtree.moviebrowse.model.Movie
import org.yellowtree.moviebrowse.model.SearchResult
import org.yellowtree.moviebrowse.util.NetworkProvider
import org.yellowtree.moviebrowse.util.Resource
import java.util.concurrent.atomic.AtomicBoolean

class SearchAPIServiceRepo {

    companion object {
        //const val BASE_URL = "https://www.omdbapi.com/?s=monster&apikey=e5606376"
        const val BASE_URL = "https://www.omdbapi.com/?apikey=d350b2c7"
        @Volatile
        private var INSTANCE : SearchAPIServiceRepo? = null
        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: SearchAPIServiceRepo().apply {
                INSTANCE = this
            }
        }
    }


    fun search(context : Context, searchContent : String) : LiveData<Resource<List<SearchResult>>> {


        val url = "$BASE_URL&s=$searchContent"

        return object : LiveData<Resource<List<SearchResult>>>() {

            private val started = AtomicBoolean(false)
            init {
                postValue(Resource.loading(null))
            }

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            val resultArray = response.optJSONArray("Search")
                            if (resultArray == null) {
                                postValue(Resource.success(null))
                            } else {
                                val resultList = parseToSearchResult(resultArray)
                                postValue(Resource.success(resultList))
                            }

                        },
                        { error ->
                            postValue(Resource.error(error.toString()))
                        })
                    NetworkProvider.getInstance(context).addToRequestQueue(jsonObjectRequest)

                }

            }
        }

    }

    fun find(context: Context, id :String) :LiveData<Resource<Movie>> {
        val url = "$BASE_URL&plot=short&i=$id"
        return object : LiveData<Resource<Movie>>() {

            private val started = AtomicBoolean(false)
            init {
                postValue(Resource.loading(null))
            }

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            if (response == null) {
                                postValue(Resource.success(null))
                            } else {
                                postValue(Resource.success(parseToMovie(response)))
                            }

                        },
                        { error ->
                            postValue(Resource.error(error.toString()))
                        })
                    NetworkProvider.getInstance(context).addToRequestQueue(jsonObjectRequest)

                }

            }
        }

    }


    private fun parseToMovie(result : JSONObject) : Movie {
        val title = result.optString("Title")
        val year = result.optString("Year")
        val posterUrl = result.optString("Poster")
        val plot = result.optString("Plot")
        val director = result.optString("Director")
        val award = result.optString("Awards")
        return Movie(title,director, year, plot, posterUrl, award)


    }


    private fun parseToSearchResult(jsonArray: JSONArray)  : List<SearchResult> {
        val resultList = mutableListOf<SearchResult>()
        for(index in 0 until jsonArray.length() - 1) {
            val result = jsonArray.getJSONObject(index)
            val title = result.optString("Title")
            val year = result.optString("Year")
            val id = result.optString("imdbID")
            val type = result.optString("Type")
            val posterUrl = result.optString("Poster")
            resultList.add(SearchResult(id, title, year,  type, posterUrl))
        }
        return resultList

    }
}