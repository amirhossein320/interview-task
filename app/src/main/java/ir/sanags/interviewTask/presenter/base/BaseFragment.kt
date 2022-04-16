package ir.sanags.interviewTask.presenter.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import ir.sanags.interviewTask.presenter.main.MainActivity

open class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> viewBinding
) : Fragment() {

    private var _binding: viewBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun navigateTo(fragment: Fragment) {
        (requireActivity() as MainActivity).navigateTo(fragment)
    }

    open fun navigateToChild(fragment: Fragment, container: Int) {
        childFragmentManager.beginTransaction().replace(container, fragment).commit()
    }

    open fun popBack() = (requireActivity() as MainActivity).popBack()

    open fun getInjection() = (requireActivity() as MainActivity).getInjection()

    open fun getOrientation() = requireActivity().resources.configuration.orientation

    open fun showSnackMessage(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
}