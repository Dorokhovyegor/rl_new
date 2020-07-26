package com.nullit.rtg.ui.auth

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nullit.rtg.R
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
        Handler().postDelayed({
            motionLayout.transitionToEnd()
        }, 100)
    }
}