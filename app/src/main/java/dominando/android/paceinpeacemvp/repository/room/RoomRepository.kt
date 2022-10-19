package dominando.android.paceinpeacemvp.repository.room

import androidx.lifecycle.LiveData
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.RunningRepository

class RoomRepository(
    database: RunningDatabase
) : RunningRepository {
    private val runningDao = database.runningDao()

    override fun save(running: Running) {
        if (running.id == 0L) {
            val id = runningDao.insert(running)
            running.id = id
        } else {
            runningDao.update(running)
        }
    }

    override fun remove(vararg runnings: Running) {
        runningDao.delete(*runnings)
    }

    override fun runningById(id: Long): LiveData<Running> {
        return runningDao.runningById(id)
    }

    override fun search(term: String): LiveData<List<Running>> {
        return runningDao.search(term)
    }


}