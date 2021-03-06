package com.nullit.core.repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class JobManager {

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

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                ErrorHandling.parseDetailJsonResponse(it.readUtf8())
            }
        } catch (exception: Exception) {
            null
        }
    }
}