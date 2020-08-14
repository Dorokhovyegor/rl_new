package com.nullit.features_chat.repository

import androidx.lifecycle.LiveData
import com.nullit.features_chat.api.ApiService
import com.nullit.features_chat.api.dto.DialogDto
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
    val eventService: EventService,
    val apiService: ApiService,
    val dialogMapper: DialogMapper
    // val tokenDao: TokenDao
    // val messageDao: MessageDao
) : ChatRepository {

    val connectionState: LiveData<ChatSocketEvent>
        get() = (eventService as EventServiceImpl).socketEvent

    override suspend fun sendMessage(message: String, chatId: Int) {
        // after success save message
    }

    override suspend fun connect(chatId: Int) {
        eventService.connect(getToken(), chatId)
    }

    override suspend fun disconnect() {
        eventService.disconnect()
    }

    override suspend fun requestDialogListByPage(page: Int): List<DialogModel> {
        // val token = tokenDao.getToken()
        val result = apiService.requestDialogListByPage(getToken(), DIALOGS_PER_PAGE)
        val mappedResult = withContext(Dispatchers.Default) {
            dialogMapper.fromDialogListDtoToListDialogModel(result)
        }
        // todo save result to db
        return mappedResult
    }

    override suspend fun subscribeOnMessages(): Flow<JSONObject> {
        TODO("Not yet implemented")
        // save every message to local db
    }

    override suspend fun saveMessageToLocalDb(message: JSONObject) {
        // messageDao.saveMessageToDb
    }

    // never mind
    private fun getToken(): String {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjNmZDhkZjM2NGQ5MDI3Y2IxY2E3NDM0YzhiMTNmMzM0MzVkOGU1NWVlNzAxYTc3NGVmOGNjNzA0YjQ1YmI0NGQ3Y2NiYzhkYzQ0YzkxMGQiLCJpYXQiOjE1OTcxNTA2MjMsIm5iZiI6MTU5NzE1MDYyMywiZXhwIjoxNjI4Njg2NjIzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.gK_Qflq8e2bQ_KKYiXiz2GOoAsspJWdW7nM1oun_8pf2FDXR1oDfyexx6hEcYQjsErVPVUT4DVW9IgbgYS5pKM3HZBKIthryBpDhTZQAftEME_P_UrUmhwN0bq4S6QlKLKG4oOs1uUFfjbEod9O2a7XGMEMlYQwYNBsOZ7duh4OF0I-IMXG7h2WJQG7wzy2Pm5PMpbS481tQWY5im6cydJMYIiixI6xWbPu58nqUDfMkyF914N6HBUGF8B_XttSO1SXZwiDNw_dU5eWch6r7Rx5QtGz6nN3EWNKYMLyIAqjaFUdjnBJygiJ03m_y0-UQkyJuhWUav6kI_dSIwLUun1Tj1-hWQl0hlNRH7zkzymog3glYcpjuCWW_7jdKr288NC1Dyopsg4ayXzJo2ab2L68Awwe4flcwraPOGget-i6pKatCqx4SL8qdEBP9o9lXi8M-qEUN0MDsgv62whd6BkMdbHZC6GKU4vTMbtzLqo68DTSw8PKSkKmgq5MeEVoBbCoZOxHu2Hj_m-tZ2Y7KAqwFngWPAkJ-Rs_yGZfzX3ejUqQKOjAiwlFhw2Sr_RvKxQw903McKmhtNOmyHmzpIrvI8-D-bWUZANIWLancxjWdZgAMAG6y5qMGLDOoonaIUBe_rlSgU4jAUWZTutMqjKVXWEoN5iPxBLKeO4bhjh0"
    }

}