package com.nullit.rtg.ui.auth

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nullit.rtg.R
import com.nullit.rtg.ui.common.BaseAuthFragment

class SplashFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            subscribeObservers()
            checkUserToken()
        }, 1_200)
    }

    private fun subscribeObservers() {
        viewModel.successLogin.observe(viewLifecycleOwner, Observer { isAuthenticated ->
            mainViewModel.setAuthenticatedStatus(isAuthenticated)
            if (isAuthenticated) {
                findNavController().navigate(R.id.action_splashFragment_to_chat_navigation_graph)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }

    private fun checkUserToken() {
        viewModel.requestUserAuthStatus()
    }
}