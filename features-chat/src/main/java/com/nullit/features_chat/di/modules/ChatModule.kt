package com.nullit.features_chat.di.modules

import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides

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

}