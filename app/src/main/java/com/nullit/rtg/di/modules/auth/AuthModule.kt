package com.nullit.rtg.di.modules.auth

import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.rtg.api.ApiService
import com.nullit.rtg.di.scopes.AuthScope
import com.nullit.rtg.repository.auth.AuthRepository
import com.nullit.rtg.repository.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideOpenApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        apiService: ApiService,
        userDao: UserDao,
        sharedPrefsManager: SharedPrefsManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            apiService = apiService,
            userDao = userDao,
            sharedPrefsManager = sharedPrefsManager
        )
    }


}