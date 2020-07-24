package com.nullit.rtg.framework.presentation.auth.state

import com.nullit.rtg.framework.presentation.auth.state.LoginFields.LoginError.Companion.ERROR_FIELDS_SHOULD_NOT_BE_EMPTY
import com.nullit.rtg.framework.presentation.auth.state.LoginFields.LoginError.Companion.ERROR_NONE

data class AuthViewState(
    var registrationFields: RegistrationFields = RegistrationFields(),
    var loginFields: LoginFields = LoginFields()
)

// todo эти поля еще неизвестны
data class RegistrationFields(
    var name: String? = null
) {

    class RegistrationError {

    }

}

data class LoginFields(
    var login: String? = null,
    var password: String? = null
) {
    // error input handler
    class LoginError {
        companion object {
            const val ERROR_FIELDS_SHOULD_NOT_BE_EMPTY = "fields should not be empty"
            const val ERROR_NONE = "none"
        }
    }

    fun isValidLogin(): String {
        if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
            return ERROR_FIELDS_SHOULD_NOT_BE_EMPTY
        }
        return ERROR_NONE
    }
}