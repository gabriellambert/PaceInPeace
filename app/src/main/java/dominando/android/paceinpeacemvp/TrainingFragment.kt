package dominando.android.paceinpeacemvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dominando.android.paceinpeacemvp.databinding.FragmentTrainingBinding

class TrainingFragment: Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance() : TrainingFragment {
            return TrainingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}