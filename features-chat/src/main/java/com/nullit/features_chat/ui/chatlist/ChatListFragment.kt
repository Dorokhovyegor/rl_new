package com.nullit.features_chat.ui.chatlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.BaseChatFragment
import com.nullit.features_chat.ui.chatlist.adapters.ChatListAdapter
import com.nullit.features_chat.ui.models.DialogModel
import com.nullit.features_chat.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_chat_list.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatListFragment : BaseChatFragment(), ChatListAdapter.DialogClickListener {

    @Inject
    lateinit var requestManager: RequestManager
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var chatListViewModel: ChatListViewModel
    private lateinit var recyclerAdapter: ChatListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ChatListViewModel::class.java]
        chatListViewModel.requestDialogListOnPage(0)
        initRecyclerView()
        subscribeObserver()
        if (savedInstanceState == null) {
            // load firstPage
        }
    }

    private fun subscribeObserver() {
        chatListViewModel.dialogList.observe(viewLifecycleOwner, Observer { list ->
            recyclerAdapter.apply {
                preloadGlideImages(requestManager, list)
                submitList(list)
            }
        })

        chatListViewModel.snackBar.observe(viewLifecycleOwner, Observer {snackBarMessage ->
            view?.let {
                Snackbar.make(it, snackBarMessage, Snackbar.LENGTH_LONG).show()
            }
        })

        chatListViewModel.loading.observe(viewLifecycleOwner, Observer {loading ->
            progressCircular.visibility = if (loading) View.VISIBLE else View.GONE
        })
    }

    private fun initRecyclerView() {
        chatListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerAdapter = ChatListAdapter(
                requestManager,
                this@ChatListFragment
            )
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == recyclerAdapter.itemCount.minus(1)) {
                        Log.e("ChatListFragment", "request")
                        chatListViewModel.requestDialogListOnPage(recyclerAdapter.itemCount)
                    }
                }
            })
            adapter = recyclerAdapter
        }
    }

    override fun onDialogClick(position: Int, dialog: DialogModel) {
        findNavController().navigate(
            R.id.action_chatListFragment_to_chatFragment,
            bundleOf("chatId" to dialog.dialogId)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}