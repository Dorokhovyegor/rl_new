package com.nullit.features_chat.ui.chatlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.RequestManager
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.chatlist.adapters.viewholder.DialogViewHolder
import com.nullit.features_chat.ui.models.DialogModel

class ChatListAdapter(
    private val requestManager: RequestManager,
    private val interaction: DialogClickListener? = null
) : PagingDataAdapter<DialogModel, DialogViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dialog_list_item, parent, false)
        return DialogViewHolder(view, requestManager, interaction)
    }


    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
            return
        }
    }

    interface DialogClickListener {
        fun onDialogClick(position: Int, dialog: DialogModel)
    }
}