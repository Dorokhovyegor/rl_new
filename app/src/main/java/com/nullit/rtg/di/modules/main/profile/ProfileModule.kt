package com.nullit.rtg.di.modules.main.profile

import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.utils.SharedPrefsManager
import com.nullit.features_profile.api.ProfileApiService
import com.nullit.features_profile.mappers.UserInfoMapper
import com.nullit.features_profile.repository.ProfileRepository
import com.nullit.features_profile.repository.ProfileRepositoryImpl
import com.nullit.rtg.di.scopes.ChatFeatureScope
import com.nullit.rtg.di.scopes.ProfileFeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileModule {

    @ProfileFeatureScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ProfileApiService {
        return retrofitBuilder.build().create(ProfileApiService::class.java)
    }

    @ProfileFeatureScope
    @Provides
    fun provideProfileRepository(
        apiService: ProfileApiService,
        userInfoMapper: UserInfoMapper,
        userDao: UserDao,
        sharedPrefsManager: SharedPrefsManager
    ): ProfileRepository {
        return ProfileRepositoryImpl(apiService, userDao, userInfoMapper, sharedPrefsManager)
    }


}