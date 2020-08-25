package com.nullit.core.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nullit.core.repo.WrapperResponse

abstract class BaseViewModel() : ViewModel() {

    protected val _endSession = MutableLiveData<Boolean>()
    val endSession: LiveData<Boolean>
        get() = _endSession

    protected val _snackBar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = _snackBar

    protected var _loading: MutableLiveData<Boolean>
    init {
        _loading = MutableLiveData<Boolean>()
        _loading.value = false
    }
    val progressBar: LiveData<Boolean>
        get() = _loading

    protected fun handleErrorResponse(response: WrapperResponse<*>) {
        when (response) {
            is WrapperResponse.GenericError -> {
                _snackBar.value = response.errorMessage
            }
            is WrapperResponse.NetworkError -> {
                if (response.code == 401) {
                    _endSession.value = true
                }
                _snackBar.value = response.errorMessage
            }
        }
    }

    fun onSnackBarShown() {
        _snackBar.value = null
    }
}