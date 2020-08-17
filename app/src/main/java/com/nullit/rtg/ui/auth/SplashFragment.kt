package com.nullit.rtg.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nullit.rtg.R
import com.nullit.rtg.ui.common.BaseAuthFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.Main).launch {
            delay(1_200)
            checkUserToken()
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        viewModel.successLogin.observe(viewLifecycleOwner, Observer {isAuthenticated ->
            if (isAuthenticated) {
                findNavController().navigate(R.id.action_splashFragment_to_chatListFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }

    private fun checkUserToken() {
        viewModel.requestUserAuthStatus()
    }
}