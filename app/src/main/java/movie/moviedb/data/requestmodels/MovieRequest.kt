package movie.moviedb.data.requestmodels

import movie.moviedb.data.models.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieRequest {

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
}