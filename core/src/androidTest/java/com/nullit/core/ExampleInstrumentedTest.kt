package com.nullit.core

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nullit.core.persistance.AppDataBase
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.persistance.entities.UserProperties
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    lateinit var db: AppDataBase
    lateinit var userDao: UserDao


    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).build()
        userDao = db.getUserDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_saveUserToken() = runBlocking {
        val userInformation = userDao.requestUserInfo()
        assert(userInformation == null)
        val userProperties = UserProperties(
            1,
            "dalsd",
            "dsmak",
            "daskd",
            "ksfsl",
            "sdfkl",
            "Bearer",
            "idsfosdjknkldfnlvdnxjfkvndv"
        )
        val result = userDao.insertUser(userProperties)
        assert(result == 0.toLong())
        val readValue = userDao.requestUserInfo()
        assert(readValue == userProperties)
        assert(readValue?.token?.length!! >= 10)
    }

    @Test
    fun test_savePropertiesManyTimesAndReturnTheLast() = runBlocking {
        val insertProperty1 = UserProperties(
            1, "login", "first_name", "last_name", "email", "avatar", "Bearer", "token-token-token"
        )
        val insertProperty2 = UserProperties(
            2,
            "login2",
            "first_name2",
            "last_name2",
            "email2",
            "avatar2",
            "Bearer2",
            "token-token-token2"
        )
        userDao.insertUser(insertProperty1)
        userDao.insertUser(insertProperty2)
        val result = userDao.requestUserInfo()
        assert(insertProperty2 == result)
    }

    @Test
    fun test_deleteUserProperties() = runBlocking {
        val insertProperty1 = UserProperties(
            1, "login", "first_name", "last_name", "email", "avatar", "Bearer", "token-token-token"
        )
        userDao.insertUser(insertProperty1)
        val notNullresult = userDao.requestUserInfo()
        assert(notNullresult != null)
        userDao.deleteUser()
        val nullResult = userDao.requestUserInfo()
        assert(nullResult == null)
    }
}