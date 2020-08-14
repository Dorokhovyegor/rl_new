package com.nullit.features_chat.mappers

import com.nullit.features_chat.api.dto.DialogListDto
import com.nullit.features_chat.ui.models.DialogModel
import com.nullit.features_chat.utils.convertToPrettyDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DialogMapper
@Inject
constructor(
    // private stringProvider: StringProvider // from core
) {

    companion object {
        const val MESSAGE_TEXT = "TEXT"
        const val MESSAGE_IMAGE = "IMAGE"
        const val MESSAGE_FILE = "FILE"
        const val MESSAGE_AUDIO = "AUDIO"

        const val DIALOG_PERSONAL = "personal"
        const val DIALOG_GROUP = "group"
    }

    fun fromDialogListDtoToListDialogModel(dialogListDto: DialogListDto): List<DialogModel> {
        val dialogList = ArrayList<DialogModel>()
        dialogListDto.dialogList.forEach { dialogDto ->
            val title = dialogDto.name.trim()
            val lastMessage = when (dialogDto.lastMessage.typeMessage) {
                MESSAGE_TEXT -> {
                    // todo change to stringProvider
                    dialogDto.lastMessage.textMessage
                        ?: "Пока еще никто не написал"
                }
                MESSAGE_IMAGE -> {
                    // todo change to stringProvider
                    "Изображение"
                }
                MESSAGE_FILE -> {
                    // todo change to stringProvider
                    "Файл"
                }
                MESSAGE_AUDIO -> {
                    // todo change to stringProvider
                    "Аудиосообщение"
                }
                else -> "-"
            }
            val avatar = when (dialogDto.typeChat) {
                DIALOG_PERSONAL -> {
                    if (dialogDto.userList.isNotEmpty()) {
                        dialogDto.userList[0].avatar
                            ?: "android.resource://com.nullit.feature_chat/drawable/ic_personal_chat_default_avatar"
                    } else {
                        "android.resource://com.nullit.feature_chat/drawable/ic_personal_chat_default_avatar"
                    }
                }
                DIALOG_GROUP -> "android.resource://com.nullit.feature_chat/drawable/ic_group_chat_default_avatar"
                else -> "android.resource://com.nullit.feature_chat/drawable/ic_personal_chat_default_avatar"
            }
            val dialogModel = DialogModel(
                dialogId = dialogDto.dialogId,
                title = title,
                lastMessage = lastMessage,
                avatarUri = avatar,
                timeOfLastMessage = dialogDto.lastUpdate.convertToPrettyDate()
            )
            dialogList.add(dialogModel)
        }
        return dialogList
    }
}