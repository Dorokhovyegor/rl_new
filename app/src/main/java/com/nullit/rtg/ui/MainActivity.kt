package com.nullit.rtg.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.nullit.core.ui.EndSessionListener
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.core.utils.ViewModelProviderFactory
import com.nullit.rtg.R
import com.nullit.rtg.ui.auth.AuthActivity
import com.nullit.rtg.util.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), EndSessionListener {

    @Inject
    lateinit var sharedPrefsManager: SharedPrefsManager

    lateinit var mainViewModel: MainViewModel
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val graphs =
            listOf(R.navigation.chat_navigation_graph, R.navigation.profile_navigation_graph)
        val controller = bottom_nav.setupWithNavController(
            graphs,
            supportFragmentManager,
            R.id.main_host_fragment
        )
        currentNavController = controller
    }

    override fun logOut() {
        var result = sharedPrefsManager.deleteAllProperties()
        if (result == 1) {
            navigateToAuthActivity()
        } else {
            // todo как такое возможно?
        }
    }

    private fun navigateToAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}