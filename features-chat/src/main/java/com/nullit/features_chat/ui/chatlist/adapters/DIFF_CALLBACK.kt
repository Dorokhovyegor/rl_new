package com.nullit.features_chat.ui.chatlist.adapters

import androidx.recyclerview.widget.DiffUtil
import com.nullit.features_chat.ui.models.DialogModel

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DialogModel>() {
    override fun areItemsTheSame(oldItem: DialogModel, newItem: DialogModel): Boolean {
        return oldItem.dialogId == newItem.dialogId
    }

    override fun areContentsTheSame(oldItem: DialogModel, newItem: DialogModel): Boolean {
        return oldItem == newItem
    }
}