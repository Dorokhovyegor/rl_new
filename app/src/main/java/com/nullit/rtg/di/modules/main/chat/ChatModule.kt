package com.nullit.rtg.di.modules.main.chat

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nullit.core.persistance.dao.UserDao
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.persistance.ChatDataBase
import com.nullit.features_chat.persistance.dao.ChatMemberDao
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.persistance.dao.MessageDao
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import com.nullit.rtg.di.scopes.ChatFeatureScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ChatModule {

    @ChatFeatureScope
    @Provides
    fun provideEventService(gson: Gson, jsonParser: JsonParser): EventService {
        return EventServiceImpl(gson, jsonParser)
    }

    @ChatFeatureScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder.build().create(ApiService::class.java)
    }

    @ChatFeatureScope
    @Provides
    fun providerRoomDataBase(application: Application): ChatDataBase {
        return Room.databaseBuilder(application, ChatDataBase::class.java, ChatDataBase.DB_NAME)
            .build()
    }

    @ChatFeatureScope
    @Provides
    fun provideMessageDao(chatDataBase: ChatDataBase): MessageDao {
        return chatDataBase.getMessage()
    }

    @ChatFeatureScope
    @Provides
    fun provideDialogDao(chatDataBase: ChatDataBase): DialogDao {
        return chatDataBase.getDialogDao()
    }

    @ChatFeatureScope
    @Provides
    fun provideChatMemberDao(chatDataBase: ChatDataBase): ChatMemberDao {
        return chatDataBase.catMemberDao()
    }

    @ChatFeatureScope
    @Provides
    fun provideChatRepository(
        eventService: EventService,
        apiService: ApiService,
        dialogMapper: DialogMapper,
        userDao: UserDao,
        dialogDao: DialogDao
    ): ChatRepository {
        return ChatRepositoryImpl(eventService, apiService, dialogMapper, userDao, dialogDao)
    }
}