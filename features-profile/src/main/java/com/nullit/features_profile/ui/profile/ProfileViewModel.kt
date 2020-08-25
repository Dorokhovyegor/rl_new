package com.nullit.features_profile.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.features_profile.repository.ProfileRepository
import com.nullit.features_profile.ui.presentationmodel.UserPresentationModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _userPresentationModel: MutableLiveData<UserPresentationModel> = MutableLiveData()
    val userPresentation: LiveData<UserPresentationModel>
        get() = _userPresentationModel

    fun requestUserInfo() {
        _loading.value = true
        val job = viewModelScope.launch {
            val result = profileRepository.requestUserData()
            if (result is WrapperResponse.SuccessResponse) {
                _userPresentationModel.value = result.body
            } else {
                handleErrorResponse(result)
            }
        }
        job.invokeOnCompletion {
            _loading.value = false
        }
    }

    fun updateUserInfo(firstName: String, lastName: String) {
        _loading.value = true
        val job = viewModelScope.launch {
            val result = profileRepository.updateUserData(firstName, lastName)
            if (result is WrapperResponse.SuccessResponse) {
                _userPresentationModel.value = result.body
            } else {
                handleErrorResponse(result)
            }
        }

        job.invokeOnCompletion {
            _loading.value = false
        }
    }

    fun logOut() {
        _loading.value = true
        val job = viewModelScope.launch {
            val result = profileRepository.logOut()
            if (result > 0) {
                _endSession.value = true
            }
        }

        job.invokeOnCompletion {
            _loading.value = false
        }
    }
}