package com.nullit.features_chat.ui.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.nullit.core.utils.ViewModelProviderFactory
import com.nullit.features_chat.R
import com.nullit.features_chat.ui.BaseChatFragment
import com.nullit.features_chat.ui.chatlist.adapters.ChatListAdapter
import com.nullit.features_chat.ui.chatlist.adapters.DialogsLoadStateAdapter
import com.nullit.features_chat.ui.models.DialogModel
import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatListFragment : BaseChatFragment(), ChatListAdapter.DialogClickListener, CoroutineScope {

    private var dialogsJob: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + dialogsJob

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
        chatListViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ChatListViewModel::class.java]
        initRecyclerView()
        startListeningDialogsFlow()
        initListeners()
        if (savedInstanceState == null) {
            // load firstPage
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initListeners() {
        swipeRefresh.setOnRefreshListener {
            dialogsJob.cancel()
            recyclerAdapter.refresh()
        }
    }

    private fun startListeningDialogsFlow() {
        dialogsJob.cancel()
        dialogsJob = lifecycleScope.launch {
            chatListViewModel.dialogList.collectLatest { pagingData ->
                recyclerAdapter.submitData(pagingData)
            }

            recyclerAdapter.loadStateFlow.collectLatest { loadStates ->
                swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    override fun observeSessionState() {
        chatListViewModel.endSession.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToAuthActivity()
            }
        })
    }

    private fun initRecyclerView() {
        chatListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerAdapter = ChatListAdapter(
                requestManager,
                this@ChatListFragment
            )
            adapter = recyclerAdapter.withLoadStateFooter(
                footer = DialogsLoadStateAdapter(recyclerAdapter)
            )
        }
    }

    override fun onStop() {
        super.onStop()
        dialogsJob.cancel()
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