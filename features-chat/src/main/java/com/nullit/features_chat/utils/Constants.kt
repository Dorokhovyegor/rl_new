package com.nullit.features_chat.utils

import io.socket.client.Socket

class Constants {
    companion object {
        const val BASE_URL = "http://test.royaltg.org"
        const val SOCKET_HOST_NAME = "http://test.royaltg.org:6001"
        const val SOCKET_URL = "http://test.royaltg.org/socket.io"
        const val SOCKET_PORT = 6001
        const val PATH = "socket.io"
        const val EVENT_CONNECT = Socket.EVENT_CONNECT
        const val EVENT_DISCONNECT = Socket.EVENT_DISCONNECT
        const val EVENT_CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR
        const val EVENT_CONNECT_TIMEOUT = Socket.EVENT_CONNECT_TIMEOUT
        const val EVENT_NEW_MESSAGE = ".ChatMessage"
        const val DIALOGS_PER_PAGE = 10
    }
}