package com.nullit.features_chat.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nullit.features_chat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.mrbin99.laravelechoandroid.Echo
import net.mrbin99.laravelechoandroid.EchoOptions

class ChatActivity : BaseChatActivity() {

    private val HOST = "http://test.royaltg.org:6001"
    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGMxMmE3MDY1MWUyNTM1NzRlOWYxZWVlYTZiZmQ2NDIwMThmMzEzM2M1MTY5YjkwZTQ4Y2JiZjU3ODlmNzJlY2M5OTE0YTVjMTg3ZWExMDEiLCJpYXQiOjE1OTYwMDk4NjEsIm5iZiI6MTU5NjAwOTg2MSwiZXhwIjoxNjI3NTQ1ODYxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.atL_v2qeakaQnb7YMZUshztB-DDPAB0wn4iDx9_UFikkugRyC-wJt_hMIqeMKJfr704olvkD1awLGKOSbrMkP_BsUJj49MW3bV6pSIMPkqO0101GaSyCuQz4RYJ6-u5lpKeSE4ZCDPeELOVI7Z6-zgWteLRVpZAV5GTaV2-e7bg8wI1g124TnDYWVgk6AKwtuEbJ4K41UUJ58HKoV7vh_x9QHvC3Ivk5fh2vn1mBNb4YYhxpoSVGR02YKScQB7TqwXRq1UDazI-5MTRNUcFvbX-2pZTZmsrOAoZT2wsTMCugoALTVV5DOe7OwcMMyR5bukmh4Vj78QFTiehP3LLdufJHySxUbixXOtAx2nwyPrIo96tZkwuflVWAeBua5TvAiEffuSEPLlcrp8FmPfmUQkZHFmdtVQ-zZthDbJRHjHtLhe9JJ3B3AZzkdyGeOfbUP_eNqFeK8K_JWXmNtQOZKJbFHkR2HVtr02SdVq-d88OlAJ3c2rGhFSh3ZhcX_eEBL0A2PBATnLdLbgYadY3zfVL5mahWY7_Yy9q8Clb90wmL7zlCridjs2tXArdPbSAlQkL2ugbbSzL78YEdUZlu8xF_kRosa8UgaO1NqbMtXtnl5AizJa64yN9qwAJ2WONcgidIyDSFGGMLBoTkBHEgheX80rCDAgGvjYvTrsPvz44"

    private var echo: Echo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //connectToSocket()
    }

    private fun connectToSocket() {
        val options = EchoOptions()
        options.host = HOST
        options.authEndpoint = "socket.io"
        options.headers["Authorization"] = token
        echo = Echo(options)
        echo?.connect({ success ->
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@ChatActivity, success.toString(), Toast.LENGTH_SHORT).show()
            }
            listenForEvents()
        }, { error ->
            error?.forEach {
                Log.e("ChatActivity", it.toString())
            }
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@ChatActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun listenForEvents() {
        echo?.let {
            it.channel("private-chat-1")
                .listen("NewMessage") { data ->
                    displayEvent(it.toString())
                    Log.e("ChatActivity", data.toString())
                }
        }

        echo?.privateChannel("private-chat-1")?.listen("NewMessage") { data ->
            displayEvent(data.toString())
            Log.e("ChatActivity", data.toString())
        }
    }

    private fun displayEvent(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        echo?.disconnect()

    }
}