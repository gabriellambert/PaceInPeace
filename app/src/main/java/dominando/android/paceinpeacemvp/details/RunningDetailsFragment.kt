package dominando.android.paceinpeacemvp.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dominando.android.paceinpeacemvp.R
import dominando.android.paceinpeacemvp.databinding.FragmentRunningDetailsBinding
import dominando.android.paceinpeacemvp.form.RunningFormFragment
import dominando.android.paceinpeacemvp.model.Running
import org.koin.androidx.viewmodel.ext.android.viewModel

class RunningDetailsFragment : Fragment() {

    private val viewModel: RunningDetailsViewModel by viewModel<RunningDetailsViewModel>()
    private var running: Running? = null

    private var _binding: FragmentRunningDetailsBinding? = null
    private val binding get() = _binding!!

    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunningDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(EXTRA_RUNNING_ID, -1) ?: -1
        viewModel.loadRunningDetails(id).observe(viewLifecycleOwner, Observer { running ->
            if (running != null) {
                showRunningDetails(running)
            } else {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this)
                    ?.commit()
                errorRunningNotFound()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRunningDetails(running: Running) {
        this.running = running
        binding.txtDate.text = running.date
        binding.txtDuration.text = running.duration.toString()
        binding.txtDistance.text = running.distance.toString()
    }

    private fun errorRunningNotFound() {
        binding.txtDate.text = getString(R.string.error_running_not_found)
        binding.txtDuration.visibility = View.GONE
        binding.txtDistance.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.running_details, menu)
        val shareItem = menu.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as?
                ShareActionProvider
        setShareIntent()
    }

    private fun setShareIntent() {
        val text = getString(R.string.share_text)
        shareActionProvider?.setShareIntent(Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.action_edit) {
            RunningFormFragment.newInstance(running?.id ?: 0)
                .open(requireFragmentManager())
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_RUNNING_ID = "runningId"

        fun newInstance(id: Long) = RunningDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_RUNNING_ID, id)
            }
        }
    }
}