package movie.moviedb.data.repositories

import movie.moviedb.data.database.databaseResults.PopularResults
import movie.moviedb.data.database.localCache.PopularLocalCache
import movie.moviedb.data.network.NetworkService
import movie.moviedb.ui.base.boundaryCallbacks.PopularBoundaryCallbacks
import androidx.paging.LivePagedListBuilder


class PopularRepository(
    private val service: NetworkService,
    private val popularCache: PopularLocalCache
) {

    fun popular(region: String): PopularResults {

        val dataSourceFactory = popularCache.getAllPopular()

        val boundaryCallback = PopularBoundaryCallbacks(region, service, popularCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return PopularResults(data, networkErrors)
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}