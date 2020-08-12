package com.nullit.rtg.di.modules

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nullit.rtg.api.ApiService
import com.nullit.rtg.repository.auth.AuthRepository
import com.nullit.rtg.repository.auth.AuthRepositoryImpl
import com.nullit.rtg.room.AppDataBase
import com.nullit.rtg.room.UserDao
import com.nullit.rtg.ui.BaseApplication
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
import java.util.logging.Level
import javax.inject.Singleton


@Module
class AppModule {

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
    fun providerRoomDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, AppDataBase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providerUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.getUserDao()
    }

    @Singleton
    @Provides
    fun provideOpenApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }

    @Provides
    fun providerDispatchers(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        apiService: ApiService,
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(apiService = apiService, userDao = userDao)
    }

}