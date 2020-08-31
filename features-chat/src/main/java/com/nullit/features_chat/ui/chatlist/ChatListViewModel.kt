package com.nullit.features_chat.ui.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val _listScrollPosition: MutableLiveData<Int> = MutableLiveData(0)
    val listScrollPosition: LiveData<Int>
        get() = _listScrollPosition

    fun setNewScrollPosition(scrollPosition: Int) {
        _listScrollPosition.value = scrollPosition
    }

}