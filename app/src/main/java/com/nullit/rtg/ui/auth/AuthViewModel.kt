package com.nullit.rtg.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullit.core.persistance.entities.UserProperties
import com.nullit.rtg.repository.auth.AuthRepository
import com.nullit.rtg.util.WrapperResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _snackBar = MutableLiveData<String?>()
    private val _progressBar = MutableLiveData<Boolean>(false)
    private val _successLogin = MutableLiveData<Boolean>(false)

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
            val response = authRepository.attemptLogin(login, password)
            _progressBar.value = false
            when (response) {
                is WrapperResponse.SuccessResponse -> {
                    val saveResult = authRepository.saveUserDataToDb(
                        UserProperties(
                            response.body.user.userId,
                            response.body.user.login,
                            response.body.user.firstName,
                            response.body.user.lastName,
                            response.body.user.email,
                            response.body.user.avatar,
                            response.body.tokenType,
                            response.body.token
                        )
                    )
                    if (saveResult >= 0) {
                        _snackBar.value = "Login success" // todo localized msg
                        _successLogin.value = true
                    } else {
                        _snackBar.value = "Can't insert to db" // todo localized msg
                    }
                    _progressBar.value = false
                }
                is WrapperResponse.GenericError<*> -> {
                    _snackBar.value = response.errorMessage
                    _progressBar.value = false
                }
                is WrapperResponse.NetworkError -> {
                    _snackBar.value = response.errorResponse?.message
                    _progressBar.value = false
                }
            }
        }
    }

    // показать сразу, как появился snackBar
    fun onSnackBarShown() {
        _snackBar.value = null
    }

}