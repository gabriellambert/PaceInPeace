package dominando.android.paceinpeacemvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dominando.android.paceinpeacemvp.databinding.FragmentAccountBinding

class AccountFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding

    companion object {
        const val TAG = "AccountFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}