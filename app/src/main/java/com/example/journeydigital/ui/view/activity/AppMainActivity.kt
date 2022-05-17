package com.example.journeydigital.ui.view.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.journeydigital.R
import com.example.journeydigital.databinding.ActAppMainBinding
import com.example.journeydigital.extensions.hideToolbar
import com.example.journeydigital.extensions.showToolbar
import com.example.journeydigital.ui.view.fragment.DashboardDetailFragment
import com.example.journeydigital.ui.view.fragment.DashboardFragment


class AppMainActivity : AppCompatActivity(){
     lateinit var binding: ActAppMainBinding
    private val fm = supportFragmentManager

    /**
     * Initial onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActAppMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        this.showToolbar()
        pushFragment(DashboardFragment(),false)
    }

    /**
     * Replace/add fragment and back stack management
     */
    fun pushFragment(fragment: Fragment, isBackStack: Boolean) {
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(binding.actDashboardFrameMain.id, fragment, fragment.javaClass.getSimpleName())
        if (isBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

}
