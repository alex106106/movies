package movie.moviedb.data.database.databaseResults

import movie.moviedb.data.database.entities.NowShowingEntity
import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class NowShowingResults(
        val data: LiveData<PagedList<NowShowingEntity>>,
        val networkErrors: LiveData<String>
)