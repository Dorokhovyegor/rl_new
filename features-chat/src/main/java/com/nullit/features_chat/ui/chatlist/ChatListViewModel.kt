package com.nullit.features_chat.ui.chatlist

import android.util.Log
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
    private val _listExhausted = MutableLiveData<Boolean>(false)
    val listExhausted: LiveData<Boolean>
        get() = _listExhausted

    fun requestDialogListOnPage(currentItems: Int) {
        if (!_loadingState.value!!) {
            viewModelScope.launch {
                if (_listExhausted.value!!) {
                    return@launch
                }
                _loadingState.value = true
                val currentPage = (currentItems / 10) + 1
                val result = repository.requestDialogListByPage(currentPage)
                if (result.size != 10) {
                    _listExhausted.value = true
                }
                if (result.isNotEmpty()) {
                    if (_dialogList.value == null) {
                        _dialogList.value = result
                    } else {
                        val currentList = _dialogList.value as MutableList
                        currentList += result
                        _dialogList.value = currentList
                    }
                } else {
                    _listExhausted.value = true
                }
                _loadingState.value = false
            }
        }
    }
}