package com.nullit.rtg.ui

import android.content.Intent
import android.os.Bundle
import com.nullit.features_chat.ui.ChatActivity
import com.nullit.rtg.R
import com.nullit.rtg.ui.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, ChatActivity::class.java))
    }
}