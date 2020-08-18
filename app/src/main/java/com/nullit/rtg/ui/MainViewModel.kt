package com.nullit.rtg.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var authenticated = MutableLiveData<Boolean>()

    fun setAuthenticatedStatus(value: Boolean) {
        authenticated.value = value
    }
}