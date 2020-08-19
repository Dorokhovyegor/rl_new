package com.nullit.rtg.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
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
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
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
        mainViewModel.authenticated.observe(this, Observer { authenticated ->
            if (authenticated) {
                changeToMain()
            } else {
                changeToAuth()
            }
            if (savedInstanceState == null) {
                setupBottomNavigation()
            }
        })
    }

    private fun changeToAuth() {
        bottom_nav.visibility = View.GONE
    }

    private fun changeToMain() {
        auth_host_fragment.visibility = View.GONE
        val authFragmentView = rootView.findViewById<FragmentContainerView>(R.id.auth_host_fragment)
        rootView.removeView(authFragmentView)
        navHosViewStub.inflate()
        bottom_nav.visibility = View.VISIBLE
    }
}