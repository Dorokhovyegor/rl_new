package com.nullit.core.utils

import android.content.SharedPreferences
import com.nullit.core.persistance.entities.UserProperties
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager
@Inject
constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val USER_ID_FIELD = "userId"
        const val USER_NAME_FIELD = "userName"
    }

    fun saveUserProperties(userProperties: UserProperties): Int {
        return try {
            sharedPreferences.edit().putInt(USER_ID_FIELD, userProperties.userId).apply()
            1
        } catch (e: Throwable) {
            e.printStackTrace()
            -1
        }
    }

    fun getUserId(): Int {
        return try {
            sharedPreferences.getInt(USER_ID_FIELD, -1)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            -1
        }
    }

    fun deleteAllProperties(): Int {
        return try {
            sharedPreferences.edit().clear().apply()
            1
        } catch (e: Throwable) {
            e.printStackTrace()
            -1
        }
    }

}
