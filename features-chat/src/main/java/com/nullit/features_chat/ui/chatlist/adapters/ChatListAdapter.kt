package com.nullit.features_chat.ui.chatlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.android.synthetic.main.dialog_list_item.view.*

class ChatListAdapter(
    private val requestManager: RequestManager,
    private val interaction: DialogClickListener? = null
) : RecyclerView.Adapter<ChatListAdapter.DialogViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DialogModel>() {
        override fun areItemsTheSame(oldItem: DialogModel, newItem: DialogModel): Boolean {
            return oldItem.dialogId == newItem.dialogId
        }

        override fun areContentsTheSame(oldItem: DialogModel, newItem: DialogModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        DialogRecyclerChangeCallBack(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dialog_list_item, parent, false)
        return DialogViewHolder(view, requestManager, interaction)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bind(differ.currentList.get(position))
    }

    fun submitList(list: List<DialogModel>) {
        differ.submitList(list)
    }

    fun preloadGlideImages(requestManager: RequestManager, list: List<DialogModel>) {
        for (dialog in list) {
            requestManager
                .load(dialog.avatarUri)
                .preload()
        }
    }

    inner class DialogRecyclerChangeCallBack(
        private val adapter: ChatListAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyItemRangeRemoved(position, count)
        }
    }

    class DialogViewHolder(
        view: View,
        private val requestManager: RequestManager,
        private val interaction: DialogClickListener?
    ) : BaseViewHolder<DialogModel>(view) {
        override fun bind(model: DialogModel): Unit = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onDialogClick(adapterPosition, model)
            }
            // todo change this thing
            onlineIndicator.visibility = if (adapterPosition % 2 == 0) View.VISIBLE else View.GONE
            dialogTitle.text = model.title
            lastMessage.text = model.lastMessage
            timeLastMessage.text = model.timeOfLastMessage
            requestManager
                .load(model.avatarUri)
                .transition(withCrossFade())
                .into(chatAvatar)
        }
    }

    interface DialogClickListener {
        fun onDialogClick(position: Int, dialog: DialogModel)
    }
}