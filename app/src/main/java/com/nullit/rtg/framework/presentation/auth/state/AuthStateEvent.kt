package com.nullit.rtg.framework.presentation.auth.state

sealed class AuthStateEvent {

    class LoginStateEvent(
        val login: String,
        val password: String
    ): AuthStateEvent()
    class RegistrationStateEvent(): AuthStateEvent()
    class ResetPasswordStateEvent(): AuthStateEvent()
    class None: AuthStateEvent()

}