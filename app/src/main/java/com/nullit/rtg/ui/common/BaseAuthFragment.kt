package com.nullit.rtg.ui.common

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nullit.rtg.ui.MainViewModel
import com.nullit.rtg.ui.auth.AuthViewModel
import com.nullit.rtg.ui.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseAuthFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    lateinit var viewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this, providerFactory)[AuthViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }
}