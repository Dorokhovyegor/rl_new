package com.nullit.features_chat.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nullit.features_chat.utils.Constants
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
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
    fun provideChatRepository(
        eventService: EventService
    ): ChatRepository {
        return ChatRepositoryImpl(eventService)
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

}