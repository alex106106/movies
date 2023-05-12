package movie.moviedb.data.repositories

import movie.moviedb.data.database.databaseResults.NowShowingResults
import movie.moviedb.data.database.localCache.NowShowingLocalCache
import movie.moviedb.data.network.NetworkService
import movie.moviedb.ui.base.boundaryCallbacks.NowShowingBoundaryCallbacks
import androidx.paging.LivePagedListBuilder


class NowShowingRepository(
    private val service: NetworkService,
    private val nowShowingCache: NowShowingLocalCache
) {

    fun nowShowing(region: String): NowShowingResults {
        // Get data source factory from the local cache
        val dataSourceFactory = nowShowingCache.getAllNowShowing()

        val boundaryCallback = NowShowingBoundaryCallbacks(region, service, nowShowingCache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return NowShowingResults(data, networkErrors)
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}