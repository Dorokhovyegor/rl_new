package com.nullit.features_chat.mappers

import com.nullit.core.StringProvider
import com.nullit.features_chat.chatservice.dto.MessageDto
import com.nullit.features_chat.ui.models.MessageModel
import com.nullit.features_chat.ui.models.TextMessageCurrentUser
import com.nullit.features_chat.ui.models.TextMessageOtherUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageMapper
@Inject
constructor(
    private val stringProvider: StringProvider
) {

    fun fromMessageDtoToMessageModel(messageDto: MessageDto, currentUserId: Int): MessageModel {
        return when (messageDto.dataMessage.message.userId) {
            currentUserId -> {
                TextMessageCurrentUser(
                    messageDto.dataMessage.message.messageId,
                    currentUserId,
                    stringProvider.convertToPrettyDate(messageDto.dataMessage.message.createdAt),
                    messageDto.dataMessage.message.textMessage ?: "НЛО повлияло на это сообщение, текста нет"
                )
            }
            else -> {
                TextMessageOtherUser(
                    messageDto.dataMessage.message.messageId,
                    currentUserId,
                    stringProvider.convertToPrettyDate(messageDto.dataMessage.message.createdAt),
                    messageDto.dataMessage.message.textMessage ?: "НЛО повлияло на это сообщение, текста нет"
                )
            }
        }
    }
}