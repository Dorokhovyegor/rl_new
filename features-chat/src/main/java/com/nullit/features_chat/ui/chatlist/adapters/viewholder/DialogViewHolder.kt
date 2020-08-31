package com.nullit.features_chat.ui.chatlist.adapters.viewholder

import android.view.View
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nullit.features_chat.ui.chatlist.adapters.ChatListAdapter
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.android.synthetic.main.dialog_list_item.view.*

class DialogViewHolder(
    view: View,
    private val requestManager: RequestManager,
    private val interaction: ChatListAdapter.DialogClickListener?
) : BaseViewHolder<DialogModel>(view) {
    override fun bind(model: DialogModel): Unit = with(itemView) {
        itemView.setOnClickListener {
            interaction?.onDialogClick(bindingAdapterPosition, model)
        }
        // todo change this thing
        onlineIndicator.visibility =
            if (bindingAdapterPosition % 2 == 0) View.VISIBLE else View.GONE
        dialogTitle.text = model.title
        lastMessage.text = model.lastMessage
        timeLastMessage.text = model.timeOfLastMessage
        requestManager
            .load(model.avatarUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(chatAvatar)
    }
}