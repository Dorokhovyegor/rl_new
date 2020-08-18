package com.nullit.rtg.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullit.core.StringProvider
import com.nullit.core.persistance.entities.UserProperties
import com.nullit.rtg.R
import com.nullit.rtg.mappers.UserMapper
import com.nullit.rtg.repository.auth.AuthRepository
import com.nullit.rtg.util.WrapperResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val stringProvider: StringProvider,
    private val userMapper: UserMapper
) : ViewModel() {

    private val _snackBar = MutableLiveData<String?>()
    private val _progressBar = MutableLiveData<Boolean>(false)
    private val _successLogin = MutableLiveData<Boolean>()

    val successLogin: LiveData<Boolean>
        get() = _successLogin

    val snackbar: LiveData<String?>
        get() = _snackBar

    val progressBar: LiveData<Boolean>
        get() = _progressBar

    fun login(login: String, password: String) {
        if (login.isBlank() || password.isBlank()) {
            _snackBar.value = "Заполните пустые поля"
            return
        }
        _progressBar.value = true
        viewModelScope.launch {
            when (val response = authRepository.attemptLogin(login, password)) {
                is WrapperResponse.SuccessResponse -> {
                    val preparedUserProperties = userMapper.fromLoginResponseToUserProperties(response.body)
                    val saveResult = authRepository.saveUserDataToDb(preparedUserProperties)
                    if (saveResult >= 0) {
                        _snackBar.value =
                            stringProvider.provideString(R.string.login_message_success_login)
                        _successLogin.value = true
                    } else {
                        _snackBar.value =
                            stringProvider.provideString(R.string.login_message_failed_login)
                    }
                }
                is WrapperResponse.GenericError<*> -> {
                    _snackBar.value = response.errorMessage
                }
                is WrapperResponse.NetworkError -> {
                    _snackBar.value = response.errorResponse?.message
                }
            }
            _progressBar.value = false
        }
    }

    fun requestUserAuthStatus() {
        viewModelScope.launch {
            val result = authRepository.checkUserProperties()
            _successLogin.value = result
        }
    }

    // показать сразу, как появился snackBar
    fun onSnackBarShown() {
        _snackBar.value = null
    }

}