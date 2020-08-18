package com.nullit.core

import android.app.Application
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProvider
@Inject
constructor(private val application: Application) {

    fun provideString(@StringRes res: Int): String {
        return application.getString(res)
    }

    fun provideString(@StringRes res: Int, vararg params: Int): String {
        return application.getString(res, params)
    }

    fun provideString(@StringRes res: Int, vararg params: String): String {
        return application.getString(res, params)
    }

    fun providePluralString(@PluralsRes resource: Int, vararg params: Int): String {
        return application.resources.getQuantityString(resource, params[0], params[1])
    }


    fun convertToPrettyDate(inputString: String): String {
        var newString = inputString.substringBefore(".", inputString)
        newString = newString.replace("T", " ")
        val sdf = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(newString)
        val messageTime: Long = date.time
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - messageTime
        return when {
            delta <= MINUTE_IN_MS -> {
                provideString(R.string.right_now)
            }
            delta in MINUTE_IN_MS..HOUR_IN_MS -> {
                providePluralString(
                    R.plurals.minutesAgo,
                    (delta / MINUTE_IN_MS).toInt(),
                    (delta / MINUTE_IN_MS).toInt()
                )
            }
            delta in HOUR_IN_MS..DAY_IN_MS -> {
                providePluralString(
                    R.plurals.hoursAgo,
                    (delta / HOUR_IN_MS).toInt(),
                    (delta / HOUR_IN_MS).toInt()
                )
            }
            delta in DAY_IN_MS..TWO_DAYS_IN_MS -> {
                provideString(R.string.yesterday)
            }
            else -> {
                // just date
                newString.split(" ")[0]
            }
        }
    }

    companion object {
        const val SERVER_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
        const val MINUTE_IN_MS = 60_000
        const val HOUR_IN_MS = 3_600_000
        const val DAY_IN_MS = 86_400_000
        const val TWO_DAYS_IN_MS = 172_800_000
    }

}