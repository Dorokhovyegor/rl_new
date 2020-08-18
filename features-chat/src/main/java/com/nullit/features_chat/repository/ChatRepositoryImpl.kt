package com.nullit.features_chat.repository

import androidx.lifecycle.LiveData
import com.nullit.core.generateBearerToken
import com.nullit.core.persistance.dao.UserDao
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.chatservice.ChatSocketEvent
import com.nullit.features_chat.chatservice.EventService
import com.nullit.features_chat.chatservice.EventServiceImpl
import com.nullit.features_chat.mappers.DialogMapper
import com.nullit.features_chat.ui.models.DialogModel
import com.nullit.features_chat.utils.Constants.Companion.DIALOGS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(
    private val eventService: EventService,
    private val apiService: ApiService,
    private val dialogMapper: DialogMapper,
    private val userDao: UserDao
) : ChatRepository {

    val connectionState: LiveData<ChatSocketEvent>
        get() = (eventService as EventServiceImpl).socketEvent

    override suspend fun sendMessage(message: String, chatId: Int) {
        // after success save message
    }

    override suspend fun connect(chatId: Int) {
        val userProperties = userDao.requestUserInfo()
        val token = userProperties?.token?.generateBearerToken() ?: ""
        eventService.connect(token, chatId)
    }

    override suspend fun disconnect() {
        eventService.disconnect()
    }

    override suspend fun requestDialogListByPage(page: Int): List<DialogModel> {
        val userProperties = userDao.requestUserInfo()
        val token = userProperties?.token?.generateBearerToken() ?: ""
        val result = apiService.requestDialogListByPage(
            token = token,
            qty = DIALOGS_PER_PAGE,
            page = page
        )
        // todo save result to db
        return withContext(Dispatchers.Default) {
            dialogMapper.fromDialogListDtoToListDialogModel(result)
        }
    }

    override suspend fun subscribeOnMessages(): Flow<JSONObject> {
        TODO("Not yet implemented")
        // save every message to local db
    }

    override suspend fun saveMessageToLocalDb(message: JSONObject) {
        // messageDao.saveMessageToDb
    }
}