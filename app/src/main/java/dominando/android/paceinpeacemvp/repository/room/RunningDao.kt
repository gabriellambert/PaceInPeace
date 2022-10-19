package dominando.android.paceinpeacemvp.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.sqlite.COLUMN_DATE
import dominando.android.paceinpeacemvp.repository.sqlite.COLUMN_ID
import dominando.android.paceinpeacemvp.repository.sqlite.TABLE_RUNNING

@Dao
interface RunningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(running: Running): Long

    @Update
    fun update(running: Running): Int

    @Delete
    fun delete(vararg runnings: Running): Int

    @Query("SELECT * FROM $TABLE_RUNNING WHERE $COLUMN_ID = :id")
    fun runningById(id: Long): LiveData<Running>

    @Query("""SELECT * FROM $TABLE_RUNNING WHERE $COLUMN_DATE LIKE :query ORDER BY $COLUMN_DATE""")
    fun search(query: String): LiveData<List<Running>>
}