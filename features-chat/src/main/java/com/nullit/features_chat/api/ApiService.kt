package com.nullit.features_chat.api

import com.nullit.features_chat.api.dto.DialogListDto
import com.nullit.features_chat.api.dto.SendTextMessageDto
import retrofit2.http.*

interface ApiService {

    @GET("/api/v1/chats")
    suspend fun requestDialogListByPage(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = "application/json",
        @Query("qty") qty: Int,
        @Query("page") page: Int
    ): DialogListDto

    @Multipart
    @POST("/api/v1/chat/{chatId}/message")
    suspend fun sendTextMessage(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = "application/json",
        @Path("chatId") chatId: Int,
        @Part("type") type: String = "TEXT",
        @Part("text") msg: String
    ): SendTextMessageDto

}