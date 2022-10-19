package dominando.android.paceinpeacemvp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dominando.android.paceinpeacemvp.repository.sqlite.COLUMN_ID
import dominando.android.paceinpeacemvp.repository.sqlite.TABLE_RUNNING

@Entity(tableName = TABLE_RUNNING)
data class Running(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,
    var date: String = "",
    var duration: Float = 0.0F,
    var distance: Float = 0.0F
)