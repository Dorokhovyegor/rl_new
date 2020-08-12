package com.nullit.features_chat.ui

import android.os.Bundle
import com.nullit.features_chat.R
import dagger.android.support.DaggerAppCompatActivity

class ChatActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}