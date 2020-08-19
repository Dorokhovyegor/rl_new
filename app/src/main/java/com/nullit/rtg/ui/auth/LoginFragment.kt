package com.nullit.rtg.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nullit.rtg.R
import com.nullit.rtg.ui.common.BaseAuthFragment
import com.nullit.rtg.util.setVisible
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        subscribeObservers()
    }

    override fun onStart() {
        super.onStart()
        motionLayout.transitionToEnd()
    }

    private fun initListeners() {
        loginButton.setOnClickListener {
            viewModel.login(
                loginEditText.text.toString().trim(),
                passwordEditText.text.toString().trim()
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            circularProgress.setVisible(it)
            backgroundProgress.setVisible(it)
        })

        viewModel.snackbar.observe(viewLifecycleOwner, Observer { msg ->
            msg?.let {
                Snackbar.make(view?.rootView!!, msg, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBarShown()
            }
        })

        viewModel.successLogin.observe(viewLifecycleOwner, Observer { authenticated ->
            mainViewModel.setAuthenticatedStatus(authenticated)
        })
    }
}