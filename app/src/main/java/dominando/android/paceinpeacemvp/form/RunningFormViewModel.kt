package dominando.android.paceinpeacemvp.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.RunningRepository

class RunningFormViewModel(
    private val repository: RunningRepository
) : ViewModel() {

    private val validator by lazy { RunningValidator() }

    fun loadRunning(id: Long): LiveData<Running> {
        return repository.runningById(id)
    }

    fun saveRunning(running: Running): Boolean {
        return validator.validate(running)
            .also { validated ->
                if (validated) repository.save(running)
            }
    }

}