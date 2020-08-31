package com.nullit.rtg.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.nullit.core.utils.hideKeyboard
import com.nullit.core.utils.setVisible
import com.nullit.rtg.R
import com.nullit.rtg.ui.MainActivity
import com.nullit.rtg.ui.common.BaseAuthFragment
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
        //todo handle savedInstanceState
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //todo saveInstanceState
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
            this.hideKeyboard(requireContext(), loginEditText)
        }
        passwordEditText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.login(
                    loginEditText.text.toString().trim(),
                    passwordEditText.text.toString().trim()
                )
                this.hideKeyboard(requireContext(), loginEditText)
                true
            } else {
                false
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.progressBar.observe(viewLifecycleOwner, Observer { visibility ->
            circularProgress.setVisible(visibility)
            backgroundProgress.setVisible(visibility)
        })
        viewModel.snackbar.observe(viewLifecycleOwner, Observer { msg ->
            msg?.let {
                Snackbar.make(view?.rootView!!, msg, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBarShown()
            }
        })
        viewModel.successLogin.observe(viewLifecycleOwner, Observer { authenticated ->
            if (authenticated) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        })
    }
}