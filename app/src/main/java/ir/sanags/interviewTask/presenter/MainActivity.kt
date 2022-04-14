package ir.sanags.interviewTask.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import ir.sanags.interviewTask.R
import ir.sanags.interviewTask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }

    override fun onDestroy() {
        if (supportFragmentManager.backStackEntryCount > 1) popBack()
        else super.onDestroy()
    }

    fun navigateTo(fragment: BaseFragment<ViewBinding>, container: Int = R.id.mainContainer) {
        supportFragmentManager.beginTransaction()
            .replace(container, fragment)
            .addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }

    fun popBack() {
        supportFragmentManager.popBackStack()
    }

}