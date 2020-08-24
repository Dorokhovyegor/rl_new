package com.nullit.features_chat.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.adapters.textmessage.TextMessageCurrentUserVH
import com.nullit.features_chat.ui.adapters.textmessage.TextMessageOtherUserVH
import com.nullit.features_chat.ui.models.MessageModel
import com.nullit.features_chat.ui.models.TextMessageOtherUser
import java.util.*
import kotlin.collections.ArrayList

class MessageRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val MESSAGE_OTHER_USER = 0
    val MESSAGE_CURRENT_USER = 1

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.textMessage == newItem.textMessage
        }
    }

    private val differ = AsyncListDiffer(
        MessageRecyclerChangeCallBack(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    inner class MessageRecyclerChangeCallBack(
        private val adapter: MessageRecyclerViewAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            if (count == 1) {
                adapter.notifyItemInserted(position)
            } else if (count > 1) {
                adapter.notifyItemRangeChanged(position, count, payload)
            }
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onInserted(position: Int, count: Int) {
            if (count == 1) {
                adapter.notifyItemInserted(position)
            } else if (count > 1) {
                adapter.notifyItemRangeInserted(position, count)
            }
        }

        override fun onRemoved(position: Int, count: Int) {
            if (count == 1) {
                adapter.notifyItemRemoved(position)
            } else if (count > 1) {
                adapter.notifyItemRangeRemoved(position, count)
            }
        }
    }

    fun appendToList(messageModel: MessageModel) {
        val newList = ArrayList<MessageModel>(differ.currentList)
        if (!newList.contains(messageModel)) {
            Log.e("Adapter111", "Прошло")
            Log.e("Adapter111", newList.size.toString())
            newList.add(0, messageModel)
            differ.submitList(newList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MESSAGE_CURRENT_USER -> {
                Log.e("MessageRVAdapter", "onCreateViewHolder MESSAGE_CURRENT_USER")
                TextMessageCurrentUserVH(
                    inflater.inflate(R.layout.message_current_user_item, parent, false)
                )
            }
            MESSAGE_OTHER_USER -> {
                Log.e("MessageRVAdapter", "onCreateViewHolder MESSAGE_OTHER_USER")
                TextMessageOtherUserVH(
                    inflater.inflate(R.layout.message_other_user_item, parent, false)
                )
            }
            else -> {
                Log.e("MessageRVAdapter", "onCreateViewHolder else")
                TextMessageCurrentUserVH(
                    inflater.inflate(R.layout.message_current_user_item, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position] is TextMessageOtherUser) {
            Log.e("MessageRVAdapter", "getItemViewType")
            MESSAGE_OTHER_USER
        } else {
            Log.e("MessageRVAdapter", "getItemViewType")
            MESSAGE_CURRENT_USER
        }
    }

    override fun getItemCount(): Int {
        Log.e("MessageRVAdapter", "getItemCount ${differ.currentList.size}")
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextMessageCurrentUserVH -> {
                Log.e("MessageRVAdapter", "TextMessageCurrentUserVH")
                holder.bind(differ.currentList.get(position))
            }
            is TextMessageOtherUserVH -> {
                Log.e("MessageRVAdapter", "textMessageOtherUserVH")
                holder.bind(differ.currentList.get(position))
            }
        }
    }
}