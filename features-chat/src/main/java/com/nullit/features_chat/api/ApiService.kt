package com.nullit.features_chat.api

import com.nullit.features_chat.api.dto.DialogListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v1/chats")
    suspend fun requestDialogListByPage(
        @Header("Authorization") token: String,
        @Query("qty") qty: Int,
        @Query("page") page: Int
    ): DialogListDto


}