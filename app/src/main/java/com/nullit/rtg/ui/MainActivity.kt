package com.nullit.rtg.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nullit.rtg.R
import com.nullit.rtg.ui.common.BaseActivity
import com.nullit.rtg.ui.viewmodel.ViewModelProviderFactory
import com.nullit.rtg.util.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        subscribeObserver()

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
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
            R.id.nav_host_fragment,
            null
        )
        controller.observe(this, Observer { navController ->
            // нафига ?
        })
    }

    private fun subscribeObserver() {
        mainViewModel.authenticated.observe(this, Observer { authenticated ->
            bottom_nav.visibility = if (authenticated) View.VISIBLE else View.GONE
        })
    }
}