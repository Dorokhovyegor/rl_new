package com.nullit.rtg.util

import android.view.View

fun View.setVisible(visible: Boolean) {
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}