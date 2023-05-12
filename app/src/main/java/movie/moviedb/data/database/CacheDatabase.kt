package movie.moviedb.data.database

import movie.moviedb.data.database.converters.DateConverter
import movie.moviedb.data.database.dao.DaoNowShowing
import movie.moviedb.data.database.dao.TopRated
import movie.moviedb.data.database.dao.SearchDao
import movie.moviedb.data.database.entities.NowShowingEntity
import movie.moviedb.data.database.entities.PopularEntry
import movie.moviedb.data.database.entities.SearchEntry
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(
    SearchEntry::class,
        NowShowingEntity::class, PopularEntry::class), version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CacheDatabase: RoomDatabase() {

    companion object {
        private val LOG_TAG: String = CacheDatabase::class.simpleName.toString()
        private val LOCK: Any = Object()
        private val DATABSE_NAME: String = "movies"
        @Volatile
        private var sInstance: CacheDatabase? = null

        fun getInstance(context: Context): CacheDatabase{
            if (sInstance == null){
                synchronized(LOCK){
                    sInstance = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java,CacheDatabase.DATABSE_NAME)
                            .build()
                }
            }
            return sInstance!!
        }

    }

    abstract fun nowShowingDao(): DaoNowShowing
    abstract fun poplarDao(): TopRated
    abstract fun searchDao(): SearchDao

}