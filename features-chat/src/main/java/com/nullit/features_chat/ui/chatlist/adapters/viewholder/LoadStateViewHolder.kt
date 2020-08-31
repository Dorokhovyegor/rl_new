package com.nullit.features_chat.ui.chatlist.adapters.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import com.nullit.core.utils.setVisible
import com.nullit.features_chat.R
import kotlinx.android.synthetic.main.load_state_item.view.*

class LoadStateViewHolder(
    parent: ViewGroup,
    private val retry: () -> Unit
) : BaseViewHolder<LoadState>(
    LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
) {
    override fun bind(model: LoadState) = with(itemView) {
        retryButton.setOnClickListener { retry() }
        dialogProgressBar.setVisible(model is LoadState.Loading)
        retryButton.setVisible(model is LoadState.Error)
    }
}