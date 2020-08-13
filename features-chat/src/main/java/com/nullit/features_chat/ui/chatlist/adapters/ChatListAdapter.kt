package com.nullit.features_chat.ui.chatlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nullit.features_chat.R
import kotlinx.android.synthetic.main.dialog_list_item.view.*

class ChatListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = 14

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatViewHolder) {
            holder.bind()
        }
    }

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() = with(itemView) {
            dialogTitle.text = "Название чата"
            lastMessage.text = "Последнее сообщение"
            timeLastMessage.text = "15:45"
        }
    }

}