package com.nullit.rtg.framework.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nullit.rtg.business.domain.state.DataState
import com.nullit.rtg.framework.presentation.auth.state.AuthStateEvent
import com.nullit.rtg.framework.presentation.auth.state.AuthViewState
import com.nullit.rtg.framework.presentation.common.BaseViewModel
import javax.inject.Inject

class AuthViewModel
@Inject constructor(
    // todo inject repository
) : BaseViewModel<AuthStateEvent, AuthViewState>() {

    override fun initNewViewState(): AuthViewState = AuthViewState()

    override fun handleStateEvent(it: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        return when (it) {
            is AuthStateEvent.LoginStateEvent -> {
                liveData {  }
            }
            is AuthStateEvent.RegistrationStateEvent-> {
                liveData {  }
            }
            is AuthStateEvent.ResetPasswordStateEvent-> {
                liveData {  }
            }
            is AuthStateEvent.None ->{
                liveData {  }
            }
        }
    }
}