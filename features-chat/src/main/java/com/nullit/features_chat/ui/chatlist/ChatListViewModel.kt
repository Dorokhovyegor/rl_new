package com.nullit.features_chat.ui.chatlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nullit.core.repo.WrapperResponse
import com.nullit.core.ui.viewmodel.BaseViewModel
import com.nullit.features_chat.repository.ChatRepository
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatListViewModel
@Inject
constructor(
    private val repository: ChatRepository
) : BaseViewModel() {

    private val _dialogList = MutableLiveData<List<DialogModel>>()
    val dialogList: LiveData<List<DialogModel>>
        get() = _dialogList
    private val _listExhausted = MutableLiveData<Boolean>(false)
    val listExhausted: LiveData<Boolean>
        get() = _listExhausted

    fun requestDialogListOnPage(currentItems: Int) {
        if (!_loading.value!!) {
            viewModelScope.launch {
                if (_listExhausted.value!!) {
                    return@launch
                }
                _loading.value = true
                val currentPage = (currentItems / 10) + 1
                val result = repository.requestDialogListByPage(currentPage)
                if (result is WrapperResponse.SuccessResponse) {
                    if (result.body.size != 10) {
                        _listExhausted.value = true
                    }
                    if (result.body.isNotEmpty()) {
                        if (_dialogList.value == null) {
                            _dialogList.value = result.body
                        } else {
                            val currentList = _dialogList.value as MutableList
                            currentList += result.body
                            _dialogList.value = currentList
                        }
                    } else {
                        _listExhausted.value = true
                    }
                } else {
                    handleErrorResponse(result)
                }
                _loading.value = false
            }
        }
    }
}