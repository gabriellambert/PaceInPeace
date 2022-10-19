package dominando.android.paceinpeacemvp.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.ListFragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dominando.android.paceinpeacemvp.R
import dominando.android.paceinpeacemvp.model.Running
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RunningListFragment : ListFragment(),
    AdapterView.OnItemLongClickListener,
    ActionMode.Callback {
    private var actionMode: ActionMode? = null
    private val viewModel: RunningListViewModel by sharedViewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listView.onItemLongClickListener = this
        viewModel.test.observe(viewLifecycleOwner, Observer { running ->
            if (running != null) {
                showRunningDetails(running)
            }
        })
        viewModel.isInDeleteMode().observe(viewLifecycleOwner, Observer { deleteMode ->
            if (deleteMode == true) {
                showDeleteMode()
            } else {
                hideDeleteMode()
            }
        })
        viewModel.selectedRunnings()?.observe(viewLifecycleOwner, Observer { runnings ->
            if (runnings != null) {
                showSelectedRunnings(runnings)
            }
        })
        viewModel.selectionCount().observe(viewLifecycleOwner, Observer { count ->
            if (count != null) {
                updateSelectionCountText(count)
            }
        })
        viewModel.showDeletedMessage().observe(viewLifecycleOwner, Observer { count ->
            if (count != null && count > 0) {
                showMessageRunningsDeleted(count)
            }
        })
        viewModel.getRunnings()?.observe(viewLifecycleOwner, Observer { runnings ->
            if (runnings != null) {
                showRunnings(runnings)
            }
        })
        if (viewModel.getRunnings()?.value == null) {
            search()
        }
    }

    private fun showRunnings(runnings: List<Running>) {
        val adapter = RunningAdapter(requireContext(), runnings)
        listAdapter = adapter
    }

    private fun showRunningDetails(running: Running) {
        if (activity is OnRunningClickListener) {
            val listener = activity as OnRunningClickListener
            listener.onRunningClick(running)
        }
    }

    private fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(this)
        listView.onItemLongClickListener = null
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    fun hideDeleteMode() {
        listView.onItemLongClickListener = this
        for (i in 0 until listView.count) {
            listView.setItemChecked(i, false)
        }
        listView.post {
            actionMode?.finish()
            listView.choiceMode = ListView.CHOICE_MODE_NONE
        }
    }

    private fun showSelectedRunnings(runnings: List<Running>) {
        listView.post {
            for (i in 0 until listView.count) {
                val running = listView.getItemAtPosition(i) as Running
                if (runnings.find { it.id == running.id } != null) {
                    listView.setItemChecked(i, true)
                }
            }
        }
    }

    private fun updateSelectionCountText(count: Int) {
        view?.post {
            actionMode?.title =
                resources.getQuantityString(R.plurals.list_running_selected, count, count)
        }
    }

    private fun showMessageRunningsDeleted(count: Int) {
        Snackbar.make(
            listView, getString(R.string.message_runnings_deleted, count),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.undo) {
            viewModel.undoDelete()
        }.show()
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val running = l.getItemAtPosition(position) as Running
        viewModel.selectRunning(running)
    }

    fun search(text: String = "") {
        viewModel.search(text)
    }

    interface OnRunningClickListener {
        fun onRunningClick(running: Running)
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        val consumed = (actionMode == null)
        if (consumed) {
            val running = parent?.getItemAtPosition(position) as Running
            viewModel.setInDeleteMode(true)
            viewModel.selectRunning(running)
        }
        return consumed
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        activity?.menuInflater?.inflate(R.menu.running_delete_list, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_delete) {
            viewModel.deleteSelected()
            return true
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        viewModel.setInDeleteMode(false)
    }
}