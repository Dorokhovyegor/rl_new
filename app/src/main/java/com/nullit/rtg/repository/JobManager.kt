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

open class JobManager(
    private val className: String
) {

    private val jobs: HashMap<String, Job> = HashMap()

    fun addJob(methodName: String, job: Job) {
        cancelJob(methodName)
        jobs[methodName] = job
    }

    fun cancelJob(methodName: String) {
        getJob(methodName)?.cancel()
    }

    private fun getJob(methodName: String): Job? {
        if (jobs.containsKey(methodName)) {
            jobs[methodName]?.let {
                return it
            }
        }
        return null
    }

    fun cancelActiveJobs() {
        for ((methodName, job) in jobs) {
            if (job.isActive) {
                job.cancel()
            }
        }

    }

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