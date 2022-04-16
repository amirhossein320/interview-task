package ir.sanags.interviewTask.presenter.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ir.sanags.interviewTask.App
import ir.sanags.interviewTask.R
import ir.sanags.interviewTask.databinding.ActivityMainBinding
import ir.sanags.interviewTask.presenter.addresses.AddressesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        if (viewModel.isFirstInit) {
            viewModel.isFirstInit = false
            navigateToWithoutStack(AddressesFragment())
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) popBack()
        else {
            if (supportFragmentManager.fragments.last().childFragmentManager.backStackEntryCount >= 1){
                supportFragmentManager.fragments.last().childFragmentManager.popBackStack()
            }else{
                super.onBackPressed()
            }
        }
    }

    fun navigateTo(fragment: Fragment, container: Int = R.id.mainContainer) {
        supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }

    fun navigateToWithoutStack(fragment: Fragment, container: Int = R.id.mainContainer) {
        supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .commit()
    }

    fun popBack() { supportFragmentManager.popBackStack() }

    fun getInjection() = (application as App).injection
}