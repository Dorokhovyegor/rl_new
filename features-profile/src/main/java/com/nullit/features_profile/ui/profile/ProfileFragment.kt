package com.nullit.features_profile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.nullit.features_profile.R
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseProfileFragment() {

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun observeSessionState() {
        profileViewModel.endSession.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToAuthActivity()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logOut.setOnClickListener {
            profileViewModel.logOut()
        }
        avatarImageView.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileInfoFragment)
        }
        profileViewModel.requestUserInfo()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        profileViewModel.userPresentation.observe(viewLifecycleOwner, Observer {
            name.text = it.userName
            requestManager.load(it.userAvatar).into(avatarImageView)
        })

        profileViewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it) progressCircular.visibility = View.VISIBLE else progressCircular.visibility = View.GONE
        })
    }
}