package com.nullit.features_chat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nullit.core.exceptions.UnsupportedActivityException
import com.nullit.core.ui.EndSessionListener
import dagger.android.support.DaggerFragment

val ARG_CHAT = "chatId"

abstract class BaseChatFragment : DaggerFragment() {

    private var endSessionListener: EndSessionListener? = null

    abstract protected fun observeSessionState()

    protected fun navigateToAuthActivity() {
        endSessionListener?.logOut()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EndSessionListener) {
            endSessionListener = context
        } else throw UnsupportedActivityException("You need to use Activity, which implementing EndSessionListener")
    }
}