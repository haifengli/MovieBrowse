package org.yellowtree.moviebrowse.util

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class NetworkProvider constructor(context : Context){

    companion object {

        @Volatile
        private var INSTANCE : NetworkProvider? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NetworkProvider(context).apply {
                INSTANCE = this
            }
        }
    }

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req : Request<T>) {
        requestQueue.add(req)
    }
}