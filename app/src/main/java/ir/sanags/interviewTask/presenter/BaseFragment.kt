package ir.sanags.interviewTask.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

class BaseFragment<viewBinding : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
) : Fragment() {

    private var _binding: ViewBinding? = null
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

    open fun navigateTo(fragment: BaseFragment<ViewBinding>) {
        (requireActivity() as MainActivity).navigateTo(fragment)
    }

    open fun navigateToChild(fragment: BaseFragment<ViewBinding>, container: Int) {
        childFragmentManager.beginTransaction().replace(container, fragment).commit()
    }

    open fun popBack() {
        (requireActivity() as MainActivity).popBack()
    }
}