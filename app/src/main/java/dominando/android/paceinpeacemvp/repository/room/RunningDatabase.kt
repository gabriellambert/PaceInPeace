package dominando.android.paceinpeacemvp.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.sqlite.DATABASE_NAME
import dominando.android.paceinpeacemvp.repository.sqlite.DATABASE_VERSION

@Database(entities = [Running::class], version = DATABASE_VERSION)
abstract class RunningDatabase : RoomDatabase() {

    abstract fun runningDao(): RunningDao

    companion object {
        private var instance: RunningDatabase? = null

        fun getDatabase(context: Context): RunningDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RunningDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return instance as RunningDatabase
        }

        fun destroyInstance() {
            instance = null
        }
    }

}