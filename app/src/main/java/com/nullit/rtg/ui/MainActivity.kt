package com.nullit.rtg.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.nullit.core.ui.EndSessionListener
import com.nullit.rtg.R
import com.nullit.rtg.ui.auth.AuthActivity
import com.nullit.rtg.ui.viewmodel.ViewModelProviderFactory
import com.nullit.rtg.util.setupWithNavController
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), EndSessionListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var mainViewModel: MainViewModel
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        subscribeObserver(savedInstanceState)
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
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    private fun subscribeObserver(savedInstanceState: Bundle?) {

    }

    override fun logOut() {
        navigateToAuthActivity()
    }

    private fun navigateToAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}