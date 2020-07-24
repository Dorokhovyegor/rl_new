package com.nullit.rtg.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.nullit.rtg.R
import com.nullit.rtg.framework.presentation.common.BaseActivity
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}