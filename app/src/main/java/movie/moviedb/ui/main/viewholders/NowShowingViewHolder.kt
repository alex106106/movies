package movie.moviedb.ui.main.viewholders

import movie.moviedb.R
import movie.moviedb.data.database.entities.NowShowingEntity
import movie.moviedb.data.models.Movie
import movie.moviedb.ui.base.interfaces.OnMovieClickListener
import movie.moviedb.utils.Constants
import movie.moviedb.utils.DateUtils
import movie.moviedb.utils.Helpers.buildImageUrl
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions


class NowShowingViewHolder(itemView: View?,
                      val context: Context,
                      val listener: OnMovieClickListener
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var movieTitle: TextView
    var movieReleaseDate: TextView
    var moviePoster: ImageView
    var movieOverView: TextView
    var movieDetails: LinearLayout
    var ratingBar: RatingBar
    private var movie: NowShowingEntity? = null

    init{
        movieTitle = itemView!!.findViewById(R.id.single_item_movie_title)
        movieReleaseDate = itemView.findViewById(R.id.single_item_movie_release_date)
        moviePoster = itemView.findViewById(R.id.single_item_movie_image)
        movieDetails = itemView.findViewById(R.id.single_item_movie_details)
        movieOverView = itemView.findViewById(R.id.single_item_movie_overview)
        ratingBar = itemView.findViewById(R.id.movie_rating_bar)
        itemView.setOnClickListener(this)

    }

    fun bindNowShowingData(movie: NowShowingEntity?, mSharedPreferences: SharedPreferences) {
        if (movie == null) {
            return
        } else {

            this.movie = movie

            movieTitle.setText(movie.title)
            movieReleaseDate.setText(movie.voteAverage?.toString()!!)
            movieOverView.text = movie.overview
            ratingBar.rating = (movie.voteAverage?.toDouble()!! / 2).toFloat()



            if (mSharedPreferences.getBoolean(context.getString(R.string.pref_cache_data_key),true)){
                Glide.with(context).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                        .transition(withCrossFade()).into(moviePoster)
            } else{
                Glide.with(context).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                        .transition(withCrossFade()).into(moviePoster)
            }
        }
    }

    private fun convertEntryToMovieList(movie: NowShowingEntity): Movie{
        val passMovie = Movie()
        passMovie.id = movie.movieId
        passMovie.voteCount = movie.voteCount
        passMovie.video = movie.video
        passMovie.voteAverage = movie.voteAverage
        passMovie.title = movie.title
        passMovie.popularity = movie.popularity
        passMovie.posterPath = movie.posterPath!!
        passMovie.originalLanguage = movie.originalLanguage
        passMovie.originalTitle = movie.originalTitle
        passMovie.backdropPath = movie.backdropPath!!
        passMovie.adult = movie.adult
        passMovie.overview = movie.overview
        passMovie.releaseDate = movie.releaseDate
        passMovie.genreString = movie.genreString!!
        passMovie.contentType = Constants.CONTENT_MOVIE
        passMovie.tableName = Constants.SEARCHES
        return passMovie
    }


    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!= RecyclerView.NO_POSITION){
            listener.onMovieClickListener(convertEntryToMovieList(movie!!))
        }
    }

}