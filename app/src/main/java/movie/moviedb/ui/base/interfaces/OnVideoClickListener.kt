package movie.moviedb.ui.base.interfaces

import movie.moviedb.data.models.MovieVideo

interface OnVideoClickListener {
    fun onVideoClickListener(movieVideo: MovieVideo)
}