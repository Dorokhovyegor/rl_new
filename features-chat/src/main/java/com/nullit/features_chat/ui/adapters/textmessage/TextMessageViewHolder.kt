package com.nullit.features_chat.ui.adapters.textmessage

import android.view.View
import android.widget.TextView
import com.nullit.features_chat.ui.adapters.BaseMessageViewHolder
import com.nullit.features_chat.ui.models.MessageModel

abstract class TextMessageViewHolder(view: View) : BaseMessageViewHolder<MessageModel>(view) {
    abstract var textMessage: TextView?
}

