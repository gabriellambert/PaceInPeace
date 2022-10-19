package dominando.android.paceinpeacemvp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.RunningRepository

class RunningDetailsViewModel(
    private val repository: RunningRepository
) : ViewModel() {

    fun loadRunningDetails(id: Long): LiveData<Running> {
        return repository.runningById(id)
    }

}