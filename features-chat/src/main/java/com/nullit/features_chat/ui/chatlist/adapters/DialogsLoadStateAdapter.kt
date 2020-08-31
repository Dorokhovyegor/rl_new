package com.nullit.features_chat.ui.chatlist.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.nullit.features_chat.ui.chatlist.adapters.viewholder.LoadStateViewHolder

class DialogsLoadStateAdapter(
    private val adapter: ChatListAdapter
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent) { adapter.retry() }

}