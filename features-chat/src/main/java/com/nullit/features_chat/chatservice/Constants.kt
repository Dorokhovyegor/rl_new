package com.nullit.features_chat.chatservice

import io.socket.client.Socket

class Constants {
    companion object {
        const val SOCKET_URL = "http://test.royaltg.org:6001"
        const val AUTH_ENDPOINT = "socket.io"
        const val EVENT_CONNECT = Socket.EVENT_CONNECT
        const val EVENT_DISCONNECT = Socket.EVENT_DISCONNECT
        const val EVENT_CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR
        const val EVENT_CONNECT_TIMEOUT = Socket.EVENT_CONNECT_TIMEOUT
        const val EVENT_NEW_MESSAGE = ".NewMessage"
    }
}