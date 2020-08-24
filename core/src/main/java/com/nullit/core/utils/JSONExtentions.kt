package com.nullit.core.utils

import org.json.JSONObject

fun JSONObject.buildJSONForSocketConnection(token: String, chatId: Int): JSONObject {
    return this.apply {
        put("channel", "private-chat-${chatId}")
        put("auth", JSONObject().apply {
            put("headers", JSONObject().apply {
                put("Authorization", token)
            })
        })
    }
}