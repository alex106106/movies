package movie.moviedb.data.requestmodels

import movie.moviedb.data.models.MovieVideo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieVideosRequest {
    @SerializedName("results")
    @Expose
    var videos: List<MovieVideo>? = null
}