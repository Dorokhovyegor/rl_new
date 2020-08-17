package com.nullit.rtg.repository

import com.nullit.rtg.ui.state.Message
import com.nullit.rtg.ui.state.MessageType
import com.nullit.rtg.util.ErrorHandling
import com.nullit.rtg.util.WrapperResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class JobManager() {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): WrapperResponse<T> {
        return withContext(dispatcher) {
            try {
                val result = apiCall.invoke()
                WrapperResponse.SuccessResponse(result)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        throwable.printStackTrace()
                        WrapperResponse.GenericError<Nothing>("IOexception")
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        WrapperResponse.NetworkError<Nothing>(code, errorResponse)
                    }
                    else -> {
                        throwable.printStackTrace()
                        WrapperResponse.GenericError<Nothing>("unknown error")
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): Message? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                Message(
                    ErrorHandling.parseDetailJsonResponse(it.readUtf8()), MessageType.Snackbar
                )
            }
        } catch (exception: Exception) {
            null
        }
    }
}