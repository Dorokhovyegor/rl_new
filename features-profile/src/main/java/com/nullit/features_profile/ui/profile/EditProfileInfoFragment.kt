package com.nullit.features_profile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nullit.features_profile.R

class EditProfileInfoFragment(): BaseProfileFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun observeSessionState() {
        profileViewModel.endSession.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToAuthActivity()
            }
        })
    }
}