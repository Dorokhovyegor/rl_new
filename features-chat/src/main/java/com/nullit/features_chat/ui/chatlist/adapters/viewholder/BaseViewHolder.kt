package com.nullit.features_chat.ui.chatlist.adapters.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: T)

}