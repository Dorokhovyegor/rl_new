package com.nullit.features_chat.di.modules

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nullit.features_chat.R
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.utils.Constants
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class ChatModule {

    @Provides
    fun provideEventService(): EventService {
        return EventServiceImpl()
    }

    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.mipmap.image)
            .error(R.mipmap.image)
    }

    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }

    @Provides
    fun provideChatRepository(
        eventService: EventService,
        apiService: ApiService,
        dialogMapper: DialogMapper
    ): ChatRepository {
        return ChatRepositoryImpl(eventService, apiService, dialogMapper)
    }

}