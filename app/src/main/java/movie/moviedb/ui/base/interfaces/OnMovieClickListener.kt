package movie.moviedb.ui.base.interfaces

import movie.moviedb.data.models.Movie

interface OnMovieClickListener {
    fun onMovieClickListener(movie: Movie)
}