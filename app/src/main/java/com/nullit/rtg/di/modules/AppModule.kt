package com.nullit.rtg.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.nullit.core.Constants.Companion.SHARED_PREFS_NAME
import com.nullit.core.persistance.MainDataBase
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.features_chat.R
import com.nullit.rtg.util.Constants
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providerSharedPrefsManager(sharedPreferences: SharedPreferences): SharedPrefsManager {
        return SharedPrefsManager(sharedPreferences)
    }


    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.mipmap.image)
            .error(R.mipmap.image)
    }

    @Singleton
    @Provides
    fun provideJsonParser(): JsonParser {
        return JsonParser()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providerRoomDataBase(application: Application): MainDataBase {
        return Room.databaseBuilder(application, MainDataBase::class.java, MainDataBase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providerUserDao(mainDataBase: MainDataBase): UserDao {
        return mainDataBase.getUserDao()
    }

    @Singleton
    @Provides
    fun providerDispatchers(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}