package com.nullit.features_chat.ui.chatlist

import androidx.paging.PagingData
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.repository.ChatRepositoryImpl
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatListViewModel
@Inject
constructor(
    private val repository: ChatRepository
) : BaseViewModel() {

    val dialogList: Flow<PagingData<DialogModel>>
        get() = (repository as ChatRepositoryImpl).pager

}