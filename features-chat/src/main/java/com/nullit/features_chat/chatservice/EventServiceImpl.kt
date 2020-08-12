package com.nullit.features_chat.chatservice

import android.util.Log
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.Transport
import org.json.JSONObject
import java.net.URISyntaxException
import javax.inject.Inject


class EventServiceImpl
@Inject
constructor() : EventService {

    lateinit var socket: Socket
    var eventListener: SocketEventListener? = null

    private fun prepareOptionsAndReturn(): IO.Options {
        // for debug
        val options: IO.Options = IO.Options()
        val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYzliNDhiNmRiNGQ0NmFiM2NjZWUwMWQxMGYwMWI2ZTk3ZDg4YTkzYjdiODlmMDQwNjhkNGNiZGMyODNkZTg4ZWE0YmU5ODg5OWMxOWExZmIiLCJpYXQiOjE1OTcyNTcxOTcsIm5iZiI6MTU5NzI1NzE5NywiZXhwIjoxNjI4NzkzMTk3LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.Ba6Y75UtfLPYFMcLYZwI0kBDkZ7bFwDkm9S_51ccsHG_KbLHZl2oAmC4lAYFMdZ6rOxqvowKZD1YQSW_-TdNIZLsUUf4nK3QksyLJyWXNkSwXjxRzoRzsZR4H_Pm8qDy-mrL7oHpRGxyK42NZZivPeekJBPCwzL0a4YypjVmm5L48giNrIIPGZ0-GSGw7BRM_QMHKwNatI-evr3ADIpa8jfMZnroubIHiN8C3MSqTh10Otwh01mSn-LjXa4duiWWNc7ASY4EVLL4VZ9SALmPS7Y5aeEWdsWLFcWTigYAgjHqIw-AKLR3jcJTz6xPCexIkqtv8sMSVKZCw9Eh_S0b_VmFUeYrEh17v8fCRamSwH8PKuiLh1vJ6CZqT6JqGbPLT5cqaxeVyKAta0fq3WbkabtMOxFOOROqpFTR1aDKaGgB-5fAAsiDGmtTWyHVM8YUqGkakGasyet42j7NVS6jHq1wj00l1sEomIa7f92R6Pe1rf26WEjjnexEt9Ku2n70CuSKIploqFa3nPMdUW-y1sLkPWFfHHF88G4vlV2XtHpcRtBNaA9qlR4Fjn2rUkJDt3JFVxy7UL2HH8eqTCNkUFAsyx1_HD2eMJVkui1ImUPBOZEcuWvF6v4YDEqDLtGSIdboeyg4jBlP2azbAjdNaL3hvkJim7ZT6FRbsqmu66o"/*    options.hostname = Constants.SOCKET_HOST_NAME*/
        //    options.port = Constants.SOCKET_PORT
        /*    options.path = Constants.PATH
            options.query = "auth_token=" + token*/

        return options
    }

    @SuppressWarnings("unchecked")
    override suspend fun connect(token: String, chatId: Int) {
        if (!::socket.isInitialized) {
            try {
                socket = IO.socket(Constants.SOCKET_HOST_NAME, prepareOptionsAndReturn())
                socket.connect()
                socket.emit("subscribe", JSONObject().apply {
                    put("channel", "private-chat-1")
                    put("auth", JSONObject().apply {
                        put("headers", JSONObject().apply {
                            put("Authorization", token)
                        })
                    })
                }).on("App\\Events\\ChatMessage") { message ->
                    message?.forEach {
                        Log.e("EventServiceImpl", it.toString())
                    }
                }
                socket.io().on(Manager.EVENT_TRANSPORT) { args ->
                    val transport: Transport = args[0] as Transport
                    transport.on(Transport.EVENT_REQUEST_HEADERS) { args ->
                        val headers = args[0] as MutableMap<String, List<String>>
                        // modify request headers
                        Log.e("EventServiceImpl", "headers ${headers}")
                    }

                    transport.on(Transport.EVENT_RESPONSE_HEADERS,
                        Emitter.Listener { args ->
                            val headers =
                                args[0] as Map<String, List<String>>
                            // access response headers
                           // val cookie = headers["Set-Cookie"]!![0]
                        })
                }

                socket.on(Constants.EVENT_CONNECT) {
                    Log.e("EventServiceImpl", "#1 connected")
                    eventListener?.onConnect(it)
                }
                socket.on(Constants.EVENT_CONNECT_TIMEOUT) {
                    Log.e("EventServiceImpl", "#2 $it")
                    eventListener?.onConnectTimeout(it)
                }
                socket.on(Constants.EVENT_NEW_MESSAGE) {
                    Log.e("EventServiceImpl", "#3 $it")
                    eventListener?.onNewMessage(it)
                }
                socket.on(Constants.EVENT_CONNECT_ERROR) {
                    Log.e("EventServiceImpl", "#4 ${it[0]}")
                    eventListener?.onConnectError(it)

                }
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }
    }

    override fun setSocketEventListener(eventListener: SocketEventListener) {
        this.eventListener = eventListener
    }

    override suspend fun disconnect() {
        socket.disconnect()
        if (!socket.connected()) {
            eventListener?.onDisconnect()
        }
    }
}