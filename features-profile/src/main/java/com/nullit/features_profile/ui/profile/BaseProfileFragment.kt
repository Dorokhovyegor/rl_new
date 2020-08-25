package com.nullit.features_profile.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nullit.core.exceptions.UnsupportedActivityException
import com.nullit.core.ui.EndSessionListener
import com.nullit.core.utils.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseProfileFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private var endSessionListener: EndSessionListener? = null
    lateinit var profileViewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run {
            profileViewModel =
                ViewModelProvider(this, viewModelProviderFactory)[ProfileViewModel::class.java]
        }
    }

    override fun onResume() {
        super.onResume()
        observeSessionState()
    }

    abstract protected fun observeSessionState()

    protected fun navigateToAuthActivity() {
        endSessionListener?.logOut()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EndSessionListener) {
            endSessionListener = context
        } else throw UnsupportedActivityException("You need to use Activity, which implementing EndSessionListener")
    }
}