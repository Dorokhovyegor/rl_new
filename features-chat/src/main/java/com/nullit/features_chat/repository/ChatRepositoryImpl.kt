package com.nullit.features_chat.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.nullit.core.generateBearerToken
import com.nullit.core.persistance.dao.UserDao
import com.nullit.core.repo.JobManager
import com.nullit.core.repo.WrapperResponse
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.api.dto.SendTextMessageDto
import com.nullit.features_chat.chatservice.ChatSocketEvent
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.chatservice.dto.MessageDto
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.persistance.dao.DialogDao
import com.nullit.features_chat.repository.pagination.PageKeyedDialogMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
    private val eventService: EventService,
    private val apiService: ApiService,
    private val dialogMapper: DialogMapper,
    private val userDao: UserDao,
    private val dialogDao: DialogDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : JobManager(), ChatRepository {

    val pager = Pager(
        config = PagingConfig(
            pageSize = PageKeyedDialogMediator.PAGE_SIZE,
            initialLoadSize = PageKeyedDialogMediator.PAGE_SIZE * 2
        ),
        remoteMediator = PageKeyedDialogMediator(dialogDao, apiService, userDao, dialogMapper),
        pagingSourceFactory = {
            dialogDao.pagingSource()
        }).flow.map { pagingData ->
        pagingData.map { dialogEntity ->
            dialogMapper.fromDialogEntityToDialogModel(dialogEntity)
        }
    }

    lateinit var token: String
    val connectionState: LiveData<ChatSocketEvent>
        get() = eventService.socketEvent

    override suspend fun sendMessage(
        message: String,
        chatId: Int
    ): WrapperResponse<SendTextMessageDto> {
        return safeApiCall(dispatcher) {
            apiService.sendTextMessage(
                token = token.generateBearerToken(),
                chatId = chatId,
                msg = message
            )
        }
    }

    override suspend fun connect(chatId: Int) {
        val userProperties = userDao.requestUserInfo()
        token = userProperties?.token?.generateBearerToken() ?: ""
        eventService.connect(token, chatId)
    }

    @ExperimentalCoroutinesApi
    fun messageFlow(): Flow<MessageDto> {
        return eventService.messageFlow()
    }

    override suspend fun disconnect() {
        eventService.disconnect()
    }

    override suspend fun saveMessageToLocalDb(message: JSONObject) {
        // messageDao.saveMessageToDb
    }
}