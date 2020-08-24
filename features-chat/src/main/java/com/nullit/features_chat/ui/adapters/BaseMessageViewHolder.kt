package com.nullit.features_chat.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMessageViewHolder<MessageModel>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(msg: MessageModel)
}