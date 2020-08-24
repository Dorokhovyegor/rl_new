package com.nullit.features_chat.ui.adapters.textmessage

import android.view.View
import android.widget.TextView
import com.nullit.features_chat.ui.models.MessageModel
import kotlinx.android.synthetic.main.message_current_user_item.view.*

class TextMessageCurrentUserVH(view: View) : TextMessageViewHolder(view) {
    override var textMessage: TextView? = null
    override fun bind(msg: MessageModel) {
        textMessage = itemView.textMsg
        textMessage?.setText(msg.textMessage)
    }
}