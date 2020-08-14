package com.nullit.features_chat.ui.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatListViewModel
@Inject
constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loadingState
    private val _snackBar = MutableLiveData<String>()
    val snackBar: LiveData<String>
        get() = _snackBar
    private val _dialogList = MutableLiveData<List<DialogModel>>()
    val dialogList: LiveData<List<DialogModel>>
        get() = _dialogList

    fun requestDialogListOnPage() {
        viewModelScope.launch {
            _loadingState.value = true
            val currentPage = (_dialogList.value?.size!! / 10) + 1
            val result = repository.requestDialogListByPage(currentPage)
            if (result.isNotEmpty()) {
                val newList = _dialogList.value as MutableList
                newList += result
                _dialogList.value = newList
            }
            _loadingState.value = false
        }
    }
}