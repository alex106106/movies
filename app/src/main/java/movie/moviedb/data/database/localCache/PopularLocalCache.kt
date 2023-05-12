package movie.moviedb.data.database.localCache

import movie.moviedb.data.database.dao.TopRated
import movie.moviedb.data.database.entities.PopularEntry
import androidx.paging.DataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

class PopularLocalCache(
    private val topRated: TopRated,
    private val ioExecutor: Executor
) {

    /**
     * Insert a list of searches in the database, on a background thread.
     */
    fun insert(repos: List<PopularEntry>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            topRated.insert(repos)
            insertFinished()
        }
    }

    fun getAllPopular(): DataSource.Factory<Int, PopularEntry> {
        return topRated.loadAllPopular()
    }

    fun getAllItemsInPopular(): Int {
        val data  = runBlocking {
            async(Dispatchers.Default) {
                val numItems = topRated.getNumberOfRows()
                return@async numItems
            }.await()
        }
        return data

    }

}