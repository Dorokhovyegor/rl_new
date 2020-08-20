package com.nullit.rtg.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nullit.rtg.R
import dagger.android.AndroidInjector
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity

class AuthActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return null
    }
}