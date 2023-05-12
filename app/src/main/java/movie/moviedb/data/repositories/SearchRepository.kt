package movie.moviedb.data.repositories

import movie.moviedb.data.database.databaseResults.SearchResults
import movie.moviedb.data.database.localCache.SearchLocalCache
import movie.moviedb.data.network.NetworkService
import movie.moviedb.ui.base.boundaryCallbacks.SearchBoundaryCallbacks
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder

class SearchRepository(
    private val service: NetworkService,
    private val searchCache: SearchLocalCache
) {



    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): SearchResults {
//        lastRequestedPage = 1
//        requestAndSaveSearchData(query)
        val dataSourceFactory = searchCache.searchesByName(query)

        val boundaryCallback = SearchBoundaryCallbacks(query, service, searchCache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        return SearchResults(data, networkErrors)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 60
    }

}