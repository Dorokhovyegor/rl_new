package com.nullit.rtg.di.modules.main

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.nullit.core.persistance.dao.UserDao
import com.nullit.features_chat.R
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import com.nullit.rtg.di.scopes.MainScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideEventService(): EventService {
        return EventServiceImpl()
    }

    @MainScope
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @MainScope
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.mipmap.image)
            .error(R.mipmap.image)
    }

    @MainScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }

    @MainScope
    @Provides
    fun provideChatRepository(
        eventService: EventService,
        apiService: ApiService,
        dialogMapper: DialogMapper,
        userDao: UserDao
    ): ChatRepository {
        return ChatRepositoryImpl(eventService, apiService, dialogMapper, userDao)
    }
}