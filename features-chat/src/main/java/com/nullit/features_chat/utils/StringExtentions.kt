package com.nullit.features_chat.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.convertToPrettyDate(): String {
    var newString = this.substringBefore(".", this)
    newString = newString.replace("T", " ")
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date: Date = sdf.parse(newString)
    val messageTime: Long = date.time
    val currentTime = System.currentTimeMillis()
    val delta = currentTime - messageTime
    println(currentTime)
    println(messageTime)
    println(delta)
    return when {
        delta <= 60_000 -> {
            "Только что"
        }
        delta in 60_000..3600_000 -> {
            "${delta/60_000} мин назад" // string provider
        }
        delta in 3_600_000..86_400_000 -> {
            "${delta/3_600_000} ч назад" // string provider
        }
        delta in 86_400_000..172_800_000 -> {
            "Вчера" // string provider
        }
        else -> {
            newString.split(" ")[0]
        }
    }
}