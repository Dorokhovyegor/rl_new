package com.nullit.core

import android.net.Uri

fun Int.generatePathToDrawable(): String {
    return Uri.parse("android.resource://" + (R::class.java.`package`?.name ?: "") + "/" + this).toString()
}

fun String.generateBearerToken(): String {
    return if (this.contains("Bearer")) {
        this
    } else {
        "Bearer $this"
    }
}

