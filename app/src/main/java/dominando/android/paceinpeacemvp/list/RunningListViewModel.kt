package dominando.android.paceinpeacemvp.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dominando.android.paceinpeacemvp.common.SingleLiveEvent
import dominando.android.paceinpeacemvp.model.Running
import dominando.android.paceinpeacemvp.repository.RunningRepository

class RunningListViewModel(
    private val repository: RunningRepository
) : ViewModel() {

    val runningIdSelected: Long = -1
    private val searchTerm = MutableLiveData<String>()
    private val runnings = Transformations.switchMap(searchTerm) { term ->
        repository.search("%$term")
    }

    private val inDeleteMode = MutableLiveData<Boolean>().apply {
        value = false
    }
    private val selectedItems = mutableListOf<Running>()
    private val selectionCount = MutableLiveData<Int>()
    private val selectedRunnings = MutableLiveData<List<Running>>().apply {
        value = selectedItems
    }
    private val deletedItems = mutableListOf<Running>()
    private val showDeletedMessage = SingleLiveEvent<Int>()
    private val showDetailsCommand = SingleLiveEvent<Running>()

    private val testLV = MutableLiveData<Running>()
    val test: LiveData<Running> = testLV

    fun isInDeleteMode(): LiveData<Boolean> = inDeleteMode

    fun getSearchTerm(): LiveData<String>? = searchTerm

    fun getRunnings(): LiveData<List<Running>>? = runnings

    fun selectionCount(): LiveData<Int> = selectionCount

    fun selectedRunnings(): LiveData<List<Running>>? = selectedRunnings

    fun showDeletedMessage(): LiveData<Int> = showDeletedMessage

    fun showDetailsCommand(): LiveData<Running> = showDetailsCommand

    fun selectRunning(running: Running) {
        if (inDeleteMode.value == true) {
            toggleRunningSelected(running)
            if (selectedItems.size == 0) {
                inDeleteMode.value = false
            } else {
                selectionCount.value = selectedItems.size
                selectedRunnings.value = selectedItems
            }
        } else {
            testLV.postValue(running)
        }
    }

    private fun toggleRunningSelected(running: Running) {
        val existing = selectedItems.find { it.id == running.id }
        if (existing == null) {
            selectedItems.add(running)
        } else {
            selectedItems.removeAll { it.id == running.id }
        }
    }

    fun search(term: String = "") {
        searchTerm.value = term
    }

    fun setInDeleteMode(deleteMode: Boolean) {
        if (!deleteMode) {
            selectionCount.value = 0
            selectedItems.clear()
            selectedRunnings.value = selectedItems
            showDeletedMessage.value = selectedItems.size
        }
        inDeleteMode.value = deleteMode
    }

    fun deleteSelected() {
        repository.remove(*selectedItems.toTypedArray())
        deletedItems.clear()
        deletedItems.addAll(selectedItems)
        setInDeleteMode(false)
        showDeletedMessage.value = deletedItems.size
    }

    fun undoDelete() {
        if (deletedItems.isNotEmpty()) {
            for (running in deletedItems) {
                running.id = 0L
                repository.save(running)
            }
        }
    }

}