package com.nullit.rtg.framework.presentation.common

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nullit.rtg.framework.presentation.auth.AuthViewModel
import com.nullit.rtg.framework.viewmodel.ViewModelProviderFactory
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

        // cancel active jobs
        cancelActiveJobs()
    }

    private fun cancelActiveJobs() {
        //viewModel.cancelActiveJobs()
    }


}