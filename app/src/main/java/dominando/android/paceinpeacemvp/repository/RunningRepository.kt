package dominando.android.paceinpeacemvp.repository

import androidx.lifecycle.LiveData
import dominando.android.paceinpeacemvp.model.Running

interface RunningRepository {
    fun save(running: Running)
    fun remove(vararg runnings: Running)
    fun runningById(id: Long): LiveData<Running>
    fun search(term: String): LiveData<List<Running>>
}