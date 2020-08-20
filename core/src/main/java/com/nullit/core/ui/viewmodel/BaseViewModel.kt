package com.nullit.core.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nullit.core.repo.WrapperResponse

abstract class BaseViewModel() : ViewModel() {

    private val _endSession = MutableLiveData<Boolean>()
    val endSession: LiveData<Boolean>
        get() = _endSession

    protected val _snackBar = MutableLiveData<String?>()
    val snackbar: LiveData<String?>
        get() = _snackBar

    protected val _loading = MutableLiveData<Boolean>()
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