package com.nullit.rtg.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nullit.core.StringProvider
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.rtg.R
import com.nullit.rtg.mappers.UserMapper
import com.nullit.rtg.repository.auth.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val stringProvider: StringProvider,
    private val userMapper: UserMapper
) : BaseViewModel() {

    private val _successLogin = MutableLiveData<Boolean>()

    val successLogin: LiveData<Boolean>
        get() = _successLogin

    fun login(login: String, password: String) {
        if (login.isBlank() || password.isBlank()) {
            _snackBar.value = "Заполните пустые поля"
            return
        }
        val job = viewModelScope.launch {
            _loading.value = true
            val response = authRepository.attemptLogin(login, password)
            if (response is WrapperResponse.SuccessResponse) {
                val preparedUserProperties =
                    userMapper.fromLoginResponseToUserProperties(response.body)
                val saveResult = authRepository.saveUserDataToDb(preparedUserProperties)
                if (saveResult >= 0) {
                    _snackBar.value = stringProvider.provideString(R.string.login_message_success_login)
                    _successLogin.value = true
                } else {
                    _snackBar.value = stringProvider.provideString(R.string.login_message_failed_login)
                }
            } else {
                handleErrorResponse(response)
            }

        }
        job.invokeOnCompletion { error ->
            if (error == null) {
                // job completed normally
                _loading.value = false

            } else {
                // job completed with error
                _loading.value = false
            }
        }
    }

    fun requestUserAuthStatus() {
        viewModelScope.launch {
            val result = authRepository.checkUserProperties()
            _successLogin.value = result
        }
    }
}