package org.yellowtree.moviebrowse.util

data class Resource<out T>(val data : T?, val status : Status, val message : String?) {
    companion object {
        fun <T> success(data : T?) : Resource<T> {
            return Resource(data, Status.SUCCESS, null)
        }

        fun <T> error(msg: String) : Resource<T> {
            return Resource(null, Status.ERROR, msg)
        }

        fun <T> loading(data: T?) : Resource<T> {
            return Resource(data, Status.LOADING, null)
        }
    }
}



enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
