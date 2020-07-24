package com.nullit.rtg.framework.presentation.auth

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nullit.rtg.R
import com.nullit.rtg.framework.presentation.common.BaseAuthFragment
import com.nullit.rtg.framework.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

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
        Handler().postDelayed({
            motionLayout.transitionToEnd()
        }, 100)
    }
}