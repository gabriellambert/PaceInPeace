package dominando.android.paceinpeacemvp.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import dominando.android.paceinpeacemvp.R
import dominando.android.paceinpeacemvp.databinding.FragmentRunningFormBinding
import dominando.android.paceinpeacemvp.model.Running
import org.koin.androidx.viewmodel.ext.android.viewModel

class RunningFormFragment : DialogFragment() {

    private val viewModel: RunningFormViewModel by viewModel<RunningFormViewModel>()

    private var running: Running? = null

    private var _binding: FragmentRunningFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunningFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val runningId = arguments?.getLong(EXTRA_RUNNING_ID, 0) ?: -1
        if (runningId > 0) {
            viewModel.loadRunning(runningId).observe(viewLifecycleOwner, Observer { running ->
                this.running = running
                showRunning(running)
            })
        }
        binding.edtDistance.setOnEditorActionListener { _, i, _ ->
            handleKeyboardEvent(i)
        }
        dialog?.setTitle(R.string.action_new_running)
        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    private fun showRunning(running: Running) {
        binding.edtDate.setText(running.date)
        binding.edtDuration.setText(running.duration.toString())
        binding.edtDistance.setText(running.distance.toString())
    }

    private fun errorInvalidRunning() {
        Toast.makeText(requireContext(), R.string.error_invalid_running, Toast.LENGTH_SHORT).show()
    }

    private fun errorSaveRunning() {
        Toast.makeText(requireContext(), R.string.error_running_not_found, Toast.LENGTH_SHORT)
            .show()
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            saveRunning()
            return true
        }
        return false
    }

    private fun saveRunning() {
        val running = this.running ?: Running()
        val runningId = arguments?.getLong(EXTRA_RUNNING_ID, 0) ?: 0
        running.id = runningId
        running.date = binding.edtDate.text.toString()
        running.duration = binding.edtDuration.text.toString().toFloat()
        running.distance = binding.edtDistance.text.toString().toFloat()
        try {
            if (viewModel.saveRunning(running)) {
                dialog?.dismiss()
            } else {
                errorInvalidRunning()
            }
        } catch (e: java.lang.Exception) {
            errorSaveRunning()
        }
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "editDialog"
        private const val EXTRA_RUNNING_ID = "running_id"

        fun newInstance(runningId: Long = 0) = RunningFormFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_RUNNING_ID, runningId)
            }
        }

    }
}